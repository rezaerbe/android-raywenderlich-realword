package com.erbe.petsave.search.domain.model

import com.erbe.petsave.common.domain.model.animal.Animal

data class SearchResults(
    val animals: List<Animal>,
    val searchParameters: SearchParameters
)