package com.erbe.petsavemultimodule.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erbe.petsavemultimodule.R
import com.erbe.common.utils.DispatchersProvider
import com.erbe.common.utils.createExceptionHandler
import com.erbe.petsavemultimodule.main.domain.usecases.OnboardingIsComplete
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val onboardingIsComplete: OnboardingIsComplete,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    val viewEffect: SharedFlow<MainActivityViewEffect> get() = _viewEffect

    private val _viewEffect = MutableSharedFlow<MainActivityViewEffect>()

    fun onEvent(event: MainActivityEvent) {
        when (event) {
            is MainActivityEvent.DefineStartDestination -> defineStartDestination()
        }
    }

    private fun defineStartDestination() {
        val errorMessage = "Failed to check if onboarding is complete"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) { onFailure(it) }

        viewModelScope.launch(exceptionHandler) {
            val destination = withContext(dispatchersProvider.io()) {
                if (onboardingIsComplete()) {
                    R.id.nav_animalsnearyou
                } else {
                    R.id.nav_onboarding
                }
            }

            _viewEffect.emit(MainActivityViewEffect.SetStartDestination(destination))
        }
    }

    private fun onFailure(throwable: Throwable) {
        // TODO: Handle failures
    }
}