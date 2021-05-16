package com.erbe.common.domain.model.search

import com.erbe.common.domain.model.animal.Animal

data class SearchResults(
    val animals: List<Animal>,
    val searchParameters: SearchParameters
)