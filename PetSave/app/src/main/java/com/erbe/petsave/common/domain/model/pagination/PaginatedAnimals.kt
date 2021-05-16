package com.erbe.petsave.common.domain.model.pagination

import com.erbe.petsave.common.domain.model.animal.details.AnimalWithDetails

data class PaginatedAnimals(
    val animals: List<AnimalWithDetails>,
    val pagination: Pagination
)