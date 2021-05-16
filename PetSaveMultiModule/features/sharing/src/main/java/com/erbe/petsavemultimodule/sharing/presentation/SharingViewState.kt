package com.erbe.petsavemultimodule.sharing.presentation

import com.erbe.petsavemultimodule.sharing.presentation.model.UIAnimalToShare

data class SharingViewState(
    val animalToShare: UIAnimalToShare = UIAnimalToShare(image = "", defaultMessage = "")
)