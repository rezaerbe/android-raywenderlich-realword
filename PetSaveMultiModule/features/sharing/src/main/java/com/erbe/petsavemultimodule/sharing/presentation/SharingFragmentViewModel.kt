package com.erbe.petsavemultimodule.sharing.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erbe.common.domain.usecases.GetAnimalDetails
import com.erbe.common.utils.DispatchersProvider
import com.erbe.petsavemultimodule.sharing.presentation.model.mappers.UiAnimalToShareMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SharingFragmentViewModel @Inject constructor(
    private val getAnimalDetails: GetAnimalDetails,
    private val uiAnimalToShareMapper: UiAnimalToShareMapper,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    val viewState: StateFlow<SharingViewState> get() = _viewState

    private val _viewState = MutableStateFlow(SharingViewState())

    fun onEvent(event: SharingEvent) {
        when (event) {
            is SharingEvent.GetAnimalToShare -> getAnimalToShare(event.animalId)
        }
    }

    private fun getAnimalToShare(animalId: Long) {
        viewModelScope.launch {
            val animal = withContext(dispatchersProvider.io()) { getAnimalDetails(animalId) }

            _viewState.value = viewState.value.copy(
                animalToShare = uiAnimalToShareMapper.mapToView(animal)
            )
        }
    }
}