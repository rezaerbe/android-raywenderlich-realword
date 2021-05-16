package com.erbe.petsavedesign.animalsnearyou.presentation

import com.erbe.petsavedesign.common.presentation.Event
import com.erbe.petsavedesign.common.presentation.model.UIAnimal

data class AnimalsNearYouViewState(
    val loading: Boolean = true,
    val animals: List<UIAnimal> = emptyList(),
    val noMoreAnimalsNearby: Boolean = false,
    val failure: Event<Throwable>? = null
)