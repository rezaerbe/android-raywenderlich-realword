package com.erbe.petsavemultimodule.onboarding.domain.usecases

import com.erbe.common.domain.repositories.AnimalRepository
import javax.inject.Inject

class StoreOnboardingData @Inject constructor(
    private val repository: AnimalRepository
) {

    suspend operator fun invoke(postcode: String, distance: String) {
        repository.storeOnboardingData(postcode, distance.toInt())
    }
}