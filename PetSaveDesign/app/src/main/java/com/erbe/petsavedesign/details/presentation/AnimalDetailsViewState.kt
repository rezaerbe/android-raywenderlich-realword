package com.erbe.petsavedesign.details.presentation

import com.erbe.petsavedesign.common.presentation.model.UIAnimalDetailed

sealed class AnimalDetailsViewState {
    object Loading : AnimalDetailsViewState()

    data class AnimalDetails(
        val animal: UIAnimalDetailed,
        val adopted: Boolean = false
    ) : AnimalDetailsViewState()

    object Failure : AnimalDetailsViewState()
}