package com.erbe.petsavedesign.animalsnearyou.domain.usecases

import com.erbe.petsavedesign.common.domain.repositories.AnimalRepository
import javax.inject.Inject

class GetAnimals @Inject constructor(
    private val animalRepository: AnimalRepository
) {

    operator fun invoke() = animalRepository.getAnimals()
        .filter { it.isNotEmpty() }
}