package com.erbe.petsavesecurity.animalsnearyou.presentation

import com.erbe.petsavesecurity.core.presentation.Event
import com.erbe.petsavesecurity.core.presentation.model.UIAnimal

data class AnimalsNearYouViewState(
    val loading: Boolean = true,
    val animals: List<UIAnimal> = emptyList(),
    val noMoreAnimalsNearby: Boolean = false,
    val failure: Event<Throwable>? = null
)