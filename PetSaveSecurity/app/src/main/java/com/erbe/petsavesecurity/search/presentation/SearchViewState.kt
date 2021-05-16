package com.erbe.petsavesecurity.search.presentation

import com.erbe.petsavesecurity.core.presentation.Event
import com.erbe.petsavesecurity.core.presentation.model.UIAnimal

data class SearchViewState(
    val noSearchQueryState: Boolean = true,
    val searchResults: List<UIAnimal> = emptyList(),
    val ageMenuValues: Event<List<String>> = Event(emptyList()),
    val typeMenuValues: Event<List<String>> = Event(emptyList()),
    val searchingRemotely: Boolean = false,
    val noResultsState: Boolean = false,
    val failure: Event<Throwable>? = null
)