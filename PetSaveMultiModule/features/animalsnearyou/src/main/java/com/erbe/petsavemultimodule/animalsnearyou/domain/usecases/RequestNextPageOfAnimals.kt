package com.erbe.petsavemultimodule.animalsnearyou.domain.usecases

import com.erbe.common.domain.model.NoMoreAnimalsException
import com.erbe.common.domain.model.pagination.Pagination
import com.erbe.common.domain.repositories.AnimalRepository
import javax.inject.Inject

class RequestNextPageOfAnimals @Inject constructor(private val animalRepository: AnimalRepository) {
    suspend operator fun invoke(
        pageToLoad: Int,
        pageSize: Int = Pagination.DEFAULT_PAGE_SIZE
    ): Pagination {
        val (animals, pagination) = animalRepository.requestMoreAnimals(pageToLoad, pageSize)

        if (animals.isEmpty()) {
            throw NoMoreAnimalsException("No animals nearby :(")
        }

        animalRepository.storeAnimals(animals)

        return pagination
    }
}