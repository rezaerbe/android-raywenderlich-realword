package com.erbe.petsavemultimodule.animalsnearyou.presentation.animaldetails

import com.erbe.petsavemultimodule.animalsnearyou.presentation.animaldetails.model.UIAnimalDetailed

sealed class AnimalDetailsViewState {
    object Loading : AnimalDetailsViewState()

    data class AnimalDetails(
        val animal: UIAnimalDetailed
    ) : AnimalDetailsViewState()

    object Failure : AnimalDetailsViewState()
}