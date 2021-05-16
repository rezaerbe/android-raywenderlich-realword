package com.erbe.petsavemultimodule.animalsnearyou.presentation.animaldetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erbe.petsavemultimodule.animalsnearyou.presentation.animaldetails.model.mappers.UiAnimalDetailsMapper
import com.erbe.common.domain.model.animal.details.AnimalWithDetails
import com.erbe.common.domain.usecases.GetAnimalDetails
import com.erbe.common.utils.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AnimalDetailsFragmentViewModel @Inject constructor(
    private val uiAnimalDetailsMapper: UiAnimalDetailsMapper,
    private val getAnimalDetails: GetAnimalDetails,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    val state: LiveData<AnimalDetailsViewState> get() = _state
    private val _state = MutableLiveData<AnimalDetailsViewState>()

    init {
        _state.value = AnimalDetailsViewState.Loading
    }

    fun handleEvent(event: AnimalDetailsEvent) {
        when (event) {
            is AnimalDetailsEvent.LoadAnimalDetails -> subscribeToAnimalDetails(event.animalId)
        }
    }

    private fun subscribeToAnimalDetails(animalId: Long) {
        viewModelScope.launch {
            try {
                val animal = withContext(dispatchersProvider.io()) { getAnimalDetails(animalId) }

                onAnimalsDetails(animal)
            } catch (t: Throwable) {
                onFailure(t)
            }
        }
    }

    private fun onAnimalsDetails(animal: AnimalWithDetails) {
        val animalDetails = uiAnimalDetailsMapper.mapToView(animal)
        _state.value = AnimalDetailsViewState.AnimalDetails(animalDetails)
    }

    private fun onFailure(failure: Throwable) {
        _state.value = AnimalDetailsViewState.Failure
    }
}