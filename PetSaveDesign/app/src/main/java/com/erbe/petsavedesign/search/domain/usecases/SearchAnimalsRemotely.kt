package com.erbe.petsavedesign.search.domain.usecases

import com.erbe.petsavedesign.common.domain.model.NoMoreAnimalsException
import com.erbe.petsavedesign.common.domain.model.pagination.Pagination
import com.erbe.petsavedesign.common.domain.model.pagination.Pagination.Companion.DEFAULT_PAGE_SIZE
import com.erbe.petsavedesign.common.domain.repositories.AnimalRepository
import com.erbe.petsavedesign.search.domain.model.SearchParameters
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.isActive
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class SearchAnimalsRemotely @Inject constructor(
    private val animalRepository: AnimalRepository
) {

    suspend operator fun invoke(
        pageToLoad: Int,
        searchParameters: SearchParameters,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Pagination {
        val (animals, pagination) =
            animalRepository.searchAnimalsRemotely(pageToLoad, searchParameters, pageSize)

        if (!coroutineContext.isActive) {
            throw CancellationException("Cancelled because new data was requested")
        }

        if (animals.isEmpty()) {
            throw NoMoreAnimalsException("Couldn't find more animals that match the search parameters.")
        }

        animalRepository.storeAnimals(animals)

        return pagination
    }
}