package com.erbe.petsavemultimodule.main.presentation

import androidx.annotation.NavigationRes

sealed class MainActivityViewEffect {
    data class SetStartDestination(@NavigationRes val destination: Int) : MainActivityViewEffect()
}