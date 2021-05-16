package com.erbe.petsavesecurity.core.domain.model.pagination

import com.erbe.petsavesecurity.core.domain.model.animal.AnimalWithDetails

data class PaginatedAnimals(
    val animals: List<AnimalWithDetails>,
    val pagination: Pagination
)