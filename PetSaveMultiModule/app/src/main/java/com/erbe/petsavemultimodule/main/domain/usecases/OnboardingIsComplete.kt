package com.erbe.petsavemultimodule.main.domain.usecases

import com.erbe.common.domain.repositories.AnimalRepository
import javax.inject.Inject

class OnboardingIsComplete @Inject constructor(
    private val repository: AnimalRepository
) {
    suspend operator fun invoke() = repository.onboardingIsComplete()
}