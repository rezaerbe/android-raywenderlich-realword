package com.erbe.petsave.animalsnearyou.presentation

import com.erbe.petsave.common.presentation.Event
import com.erbe.petsave.common.presentation.model.UIAnimal

data class AnimalsNearYouViewState(
    val loading: Boolean = true,
    val animals: List<UIAnimal> = emptyList(),
    val noMoreAnimalsNearby: Boolean = false,
    val failure: Event<Throwable>? = null
)