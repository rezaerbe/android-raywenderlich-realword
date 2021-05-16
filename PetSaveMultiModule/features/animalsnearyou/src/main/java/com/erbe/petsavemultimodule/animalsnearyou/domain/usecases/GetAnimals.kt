package com.erbe.petsavemultimodule.animalsnearyou.domain.usecases

import com.erbe.common.domain.repositories.AnimalRepository
import javax.inject.Inject

class GetAnimals @Inject constructor(private val animalRepository: AnimalRepository) {
    operator fun invoke() = animalRepository.getAnimals()
}