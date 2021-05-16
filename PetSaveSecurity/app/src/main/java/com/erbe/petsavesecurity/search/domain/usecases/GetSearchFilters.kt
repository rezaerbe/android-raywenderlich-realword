package com.erbe.petsavesecurity.search.domain.usecases

import com.erbe.petsavesecurity.core.domain.model.animal.AnimalWithDetails
import com.erbe.petsavesecurity.core.domain.repositories.AnimalRepository
import com.erbe.petsavesecurity.search.domain.MenuValueException
import com.erbe.petsavesecurity.search.domain.model.SearchFilters
import java.util.*
import javax.inject.Inject

class GetSearchFilters @Inject constructor(
    private val animalRepository: AnimalRepository
) {

    companion object {
        private const val DEFAULT_VALUE = "Any"
        private const val DEFAULT_VALUE_LOWERCASE = "any"
    }

    suspend operator fun invoke(): SearchFilters {
        val types = animalRepository.getAnimalTypes()

        val filteringTypes =
            if (types.any { it.toLowerCase(Locale.ROOT) == DEFAULT_VALUE_LOWERCASE }) {
                types
            } else {
                listOf(DEFAULT_VALUE) + types
            }

        if (types.isEmpty()) throw MenuValueException("No animal types")

        val ages = animalRepository.getAnimalAges()
            .map { it.name }
            .replace(AnimalWithDetails.Details.Age.UNKNOWN.name, DEFAULT_VALUE)
            .map { it.toLowerCase(Locale.ROOT).capitalize() }

        return SearchFilters(ages, filteringTypes)
    }

    private fun List<String>.replace(old: String, new: String): List<String> {
        return map { if (it == old) new else it }
    }
}