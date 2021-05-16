package com.erbe.petsavedesign.details.domain.usecases

import com.erbe.petsavedesign.common.domain.model.animal.details.AnimalWithDetails
import com.erbe.petsavedesign.common.domain.repositories.AnimalRepository
import io.reactivex.Single
import javax.inject.Inject

class AnimalDetails @Inject constructor(
    private val animalRepository: AnimalRepository
) {

    operator fun invoke(
        animalId: Long
    ): Single<AnimalWithDetails> {
        return animalRepository.getAnimal(animalId)
    }
}