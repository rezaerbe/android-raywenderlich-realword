package com.erbe.petsave.search.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.erbe.petsave.RxImmediateSchedulerRule
import com.erbe.petsave.TestCoroutineRule
import com.erbe.petsave.common.data.FakeRepository
import com.erbe.petsave.common.presentation.Event
import com.erbe.petsave.common.presentation.model.mappers.UiAnimalMapper
import com.erbe.petsave.common.utils.DispatchersProvider
import com.erbe.petsave.search.domain.usecases.GetSearchFilters
import com.erbe.petsave.search.domain.usecases.SearchAnimals
import com.erbe.petsave.search.domain.usecases.SearchAnimalsRemotely
import com.google.common.truth.Truth.assertThat
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchFragmentViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var viewModel: SearchFragmentViewModel
    private lateinit var repository: FakeRepository
    private lateinit var getSearchFilters: GetSearchFilters
    private val uiAnimalsMapper = UiAnimalMapper()

    @Before
    fun setup() {
        val dispatchersProvider = object : DispatchersProvider {
            override fun io() = Dispatchers.Main
        }

        repository = FakeRepository()
        getSearchFilters = GetSearchFilters(repository)
        viewModel = SearchFragmentViewModel(
            SearchAnimalsRemotely(repository),
            SearchAnimals(repository),
            getSearchFilters,
            uiAnimalsMapper,
            dispatchersProvider,
            CompositeDisposable()
        )
    }

    @Test
    fun `SearchFragmentViewModel remote search with success`() = testCoroutineRule.runBlockingTest {
        // Given
        val (name, age, type) = repository.remotelySearchableAnimal
        val (ages, types) = getSearchFilters()

        val expectedRemoteAnimals = repository.remoteAnimals.map { uiAnimalsMapper.mapToView(it) }

        viewModel.state.observeForever { }

        val expectedViewState = SearchViewState(
            noSearchQuery = false,
            searchResults = expectedRemoteAnimals,
            ageFilterValues = Event(ages),
            typeFilterValues = Event(types),
            searchingRemotely = false,
            noRemoteResults = false
        )

        // When
        viewModel.onEvent(SearchEvent.PrepareForSearch)
        viewModel.onEvent(SearchEvent.TypeValueSelected(type))
        viewModel.onEvent(SearchEvent.AgeValueSelected(age))
        viewModel.onEvent(SearchEvent.QueryInput(name))

        // Then
        val viewState = viewModel.state.value!!

        assertThat(viewState).isEqualTo(expectedViewState)
    }
}