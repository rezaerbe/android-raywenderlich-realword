package com.erbe.petsavesecurity.search.domain.model

import com.erbe.petsavesecurity.core.domain.model.animal.Animal

data class SearchResults(
    val animals: List<Animal>,
    val searchParameters: SearchParameters
)