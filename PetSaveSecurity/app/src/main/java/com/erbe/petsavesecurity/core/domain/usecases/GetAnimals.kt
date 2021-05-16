package com.erbe.petsavesecurity.core.domain.usecases

import com.erbe.petsavesecurity.core.domain.repositories.AnimalRepository
import javax.inject.Inject

class GetAnimals @Inject constructor(
    private val animalRepository: AnimalRepository
) {

    operator fun invoke() = animalRepository.getAnimals()
        .filter { it.isNotEmpty() }
}