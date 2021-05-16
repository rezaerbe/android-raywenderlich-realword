package com.erbe.common.domain.model.pagination

import com.erbe.common.domain.model.animal.details.AnimalWithDetails

data class PaginatedAnimals(
    val animals: List<AnimalWithDetails>,
    val pagination: Pagination
)