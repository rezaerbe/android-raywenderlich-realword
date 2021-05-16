package com.erbe.petsavemultimodule.animalsnearyou.presentation.main

import com.erbe.common.presentation.Event
import com.erbe.common.presentation.model.UIAnimal

data class AnimalsNearYouViewState(
    val loading: Boolean = true,
    val animals: List<UIAnimal> = emptyList(),
    val noMoreAnimalsNearby: Boolean = false,
    val failure: Event<Throwable>? = null
)