package com.erbe.petsavedesign.search.domain.model

import com.erbe.petsavedesign.common.domain.model.animal.Animal

data class SearchResults(
    val animals: List<Animal>,
    val searchParameters: SearchParameters
)