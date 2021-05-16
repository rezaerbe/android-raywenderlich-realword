package com.erbe.petsavedesign.search.presentation

import com.erbe.petsavedesign.common.presentation.Event
import com.erbe.petsavedesign.common.presentation.model.UIAnimal

data class SearchViewState(
    val noSearchQuery: Boolean = true,
    val searchResults: List<UIAnimal> = emptyList(),
    val ageMenuValues: Event<List<String>> = Event(emptyList()),
    val typeMenuValues: Event<List<String>> = Event(emptyList()),
    val searchingRemotely: Boolean = false,
    val noRemoteResults: Boolean = false,
    val failure: Event<Throwable>? = null
) {
    fun updateToReadyForSearch(ages: List<String>, types: List<String>): SearchViewState {
        return copy(
            ageMenuValues = Event(ages),
            typeMenuValues = Event(types)
        )
    }

    fun updateToNoSearchQuery(): SearchViewState {
        return copy(
            noSearchQuery = true,
            searchResults = emptyList(),
            noRemoteResults = false
        )
    }

    fun updateToSearching(): SearchViewState {
        return copy(
            noSearchQuery = false,
            noRemoteResults = false
        )
    }

    fun updateToSearchingRemotely(): SearchViewState {
        return copy(
            searchingRemotely = true,
            searchResults = emptyList()
        )
    }

    fun updateToHasSearchResults(animals: List<UIAnimal>): SearchViewState {
        return copy(
            noSearchQuery = false,
            searchResults = animals,
            searchingRemotely = false,
            noRemoteResults = false
        )
    }

    fun updateToNoResultsAvailable(): SearchViewState {
        return copy(searchingRemotely = false, noRemoteResults = true)
    }

    fun updateToHasFailure(throwable: Throwable): SearchViewState {
        return copy(failure = Event(throwable))
    }

    fun isInNoSearchResultsState(): Boolean {
        return noRemoteResults
    }
}