package com.erbe.petsavedesign.search.domain.usecases

import com.erbe.petsavedesign.common.domain.model.animal.details.Age
import com.erbe.petsavedesign.common.domain.repositories.AnimalRepository
import com.erbe.petsavedesign.search.domain.model.MenuValueException
import com.erbe.petsavedesign.search.domain.model.SearchFilters
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
            .replace(Age.UNKNOWN.name, DEFAULT_VALUE)
            .map { it.toLowerCase(Locale.ROOT).capitalize() }

        return SearchFilters(ages, filteringTypes)
    }

    private fun List<String>.replace(old: String, new: String): List<String> {
        return map { if (it == old) new else it }
    }
}