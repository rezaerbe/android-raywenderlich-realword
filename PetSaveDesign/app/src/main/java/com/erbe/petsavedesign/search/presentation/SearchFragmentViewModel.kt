package com.erbe.petsavedesign.search.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.erbe.logging.Logger
import com.erbe.petsavedesign.common.domain.model.NoMoreAnimalsException
import com.erbe.petsavedesign.common.domain.model.animal.Animal
import com.erbe.petsavedesign.common.domain.model.pagination.Pagination
import com.erbe.petsavedesign.common.presentation.model.mappers.UiAnimalMapper
import com.erbe.petsavedesign.common.utils.DispatchersProvider
import com.erbe.petsavedesign.common.utils.createExceptionHandler
import com.erbe.petsavedesign.search.domain.model.SearchParameters
import com.erbe.petsavedesign.search.domain.model.SearchResults
import com.erbe.petsavedesign.search.domain.usecases.GetSearchFilters
import com.erbe.petsavedesign.search.domain.usecases.SearchAnimals
import com.erbe.petsavedesign.search.domain.usecases.SearchAnimalsRemotely
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragmentViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getSearchFilters: GetSearchFilters,
    private val searchAnimals: SearchAnimals,
    private val searchAnimalsRemotely: SearchAnimalsRemotely,
    private val uiAnimalMapper: UiAnimalMapper,
    private val dispatchersProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val state: LiveData<SearchViewState>
        get() = _state


    private val _state: MutableLiveData<SearchViewState> = MutableLiveData()
    private val querySubject = BehaviorSubject.create<String>()
    private val ageSubject = BehaviorSubject.createDefault<String>("")
    private val typeSubject = BehaviorSubject.createDefault<String>("")

    private var runningJobs = mutableListOf<Job>()
    private var isLastPage = false
    private var currentPage = 0

    init {
        _state.value = SearchViewState()
    }

    fun handleEvents(event: SearchEvent) {
        when (event) {
            is SearchEvent.PrepareForSearch -> prepareForSearch()
            else -> onSearchParametersUpdate(event)
        }
    }

    private fun onSearchParametersUpdate(event: SearchEvent) {
        runningJobs.map { it.cancel() }

        resetStateIfNoRemoteResults()

        when (event) {
            is SearchEvent.QueryInput -> updateQuery(event.input)
            is SearchEvent.AgeValueSelected -> updateAgeValue(event.age)
            is SearchEvent.TypeValueSelected -> updateTypeValue(event.type)
        }
    }

    private fun resetStateIfNoRemoteResults() {
        if (state.value!!.isInNoSearchResultsState()) {
            _state.value = state.value!!.updateToSearching()
        }
    }

    private fun prepareForSearch() {
        loadMenuValues()
        setupSearchSubscription()
    }

    private fun loadMenuValues() {
        val exceptionHandler = createExceptionHandler(message = "Failed to get menu values!")

        viewModelScope.launch(exceptionHandler) {
            val (ages, types) = withContext(dispatchersProvider.io()) { getSearchFilters() }
            updateStateWithMenuValues(ages, types)
        }
    }

    private fun createExceptionHandler(message: String): CoroutineExceptionHandler {
        return viewModelScope.createExceptionHandler(message) {
            onFailure(it)
        }
    }

    private fun updateStateWithMenuValues(ages: List<String>, types: List<String>) {
        _state.value = state.value!!.updateToReadyForSearch(ages, types)
    }

    private fun setupSearchSubscription() {
        searchAnimals(querySubject, ageSubject, typeSubject)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSearchResults(it) },
                { onFailure(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun updateQuery(input: String) {
        resetPagination()

        querySubject.onNext(input)

        if (input.isEmpty()) {
            setNoSearchQueryState()
        } else {
            setSearchingState()
        }
    }


    private fun resetPagination() {
        currentPage = 0
        isLastPage = false
    }

    private fun setSearchingState() {
        _state.value = state.value!!.updateToSearching()
    }

    private fun setNoSearchQueryState() {
        _state.value = state.value!!.updateToNoSearchQuery()
    }

    private fun updateAgeValue(age: String) {
        ageSubject.onNext(age)
    }

    private fun updateTypeValue(type: String) {
        typeSubject.onNext(type)
    }

    private fun onSearchResults(searchResults: SearchResults) {
        val (animals, searchParameters) = searchResults

        if (animals.isEmpty()) {
            onEmptyCacheResults(searchParameters)
        } else {
            onAnimalList(animals)
        }
    }

    private fun onAnimalList(animals: List<Animal>) {
        _state.value =
            state.value!!.updateToHasSearchResults(animals.map { uiAnimalMapper.mapToView(it) })
    }

    private fun onEmptyCacheResults(searchParameters: SearchParameters) {
        _state.value = state.value!!.updateToSearchingRemotely()
        searchRemotely(searchParameters)
    }

    private fun searchRemotely(searchParameters: SearchParameters) {
        val exceptionHandler = createExceptionHandler(message = "Failed to search remotely.")

        val job = viewModelScope.launch(exceptionHandler) {
            val pagination = withContext(dispatchersProvider.io()) {
                Logger.d("Searching remotely...")

                searchAnimalsRemotely(++currentPage, searchParameters)
            }

            onPaginationInfoObtained(pagination)
        }

        runningJobs.add(job)

        job.invokeOnCompletion {
            it?.printStackTrace()
            runningJobs.remove(job)
        }
    }

    private fun onPaginationInfoObtained(pagination: Pagination) {
        currentPage = pagination.currentPage
        isLastPage = !pagination.canLoadMore
    }

    private fun onFailure(throwable: Throwable) {
        _state.value = if (throwable is NoMoreAnimalsException) {
            state.value!!.updateToNoResultsAvailable()
        } else {
            state.value!!.updateToHasFailure(throwable)
        }

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}