package com.erbe.petsavemultimodule.onboarding.presentation

sealed class OnboardingViewEffect {
    object NavigateToAnimalsNearYou : OnboardingViewEffect()
}