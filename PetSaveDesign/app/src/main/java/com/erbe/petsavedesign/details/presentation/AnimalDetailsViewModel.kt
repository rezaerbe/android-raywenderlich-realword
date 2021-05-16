package com.erbe.petsavedesign.details.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erbe.petsavedesign.common.domain.model.animal.details.AnimalWithDetails
import com.erbe.petsavedesign.common.presentation.model.mappers.UiAnimalDetailsMapper
import com.erbe.petsavedesign.details.domain.usecases.AnimalDetails
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class AnimalDetailsViewModel @ViewModelInject constructor(
    private val uiAnimalDetailsMapper: UiAnimalDetailsMapper,
    private val animalDetails: AnimalDetails,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val state: LiveData<AnimalDetailsViewState> get() = _state
    private val _state = MutableLiveData<AnimalDetailsViewState>()

    init {
        _state.value = AnimalDetailsViewState.Loading
    }

    fun handleEvent(event: AnimalDetailsEvent) {
        when (event) {
            is AnimalDetailsEvent.LoadAnimalDetails -> subscribeToAnimalDetails(event.animalId)
            is AnimalDetailsEvent.AdoptAnimal -> adoptAnimal()
        }
    }

    private fun subscribeToAnimalDetails(animalId: Long) {
        animalDetails(animalId)
            .delay(2L, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onAnimalsDetails(it) },
                { onFailure(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun onAnimalsDetails(animal: AnimalWithDetails) {
        val animalDetails = uiAnimalDetailsMapper.mapToView(animal)
        _state.value = AnimalDetailsViewState.AnimalDetails(animalDetails)
    }

    private fun adoptAnimal() {
        compositeDisposable.add(
            Observable.timer(2L, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _state.value =
                        (_state.value as AnimalDetailsViewState.AnimalDetails?)?.copy(adopted = true)
                }
        )
    }

    private fun onFailure(failure: Throwable) {
        _state.value = AnimalDetailsViewState.Failure
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}