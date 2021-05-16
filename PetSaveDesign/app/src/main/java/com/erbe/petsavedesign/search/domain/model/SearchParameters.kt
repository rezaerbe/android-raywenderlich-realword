package com.erbe.petsavedesign.search.domain.model

import java.util.*

data class SearchParameters(
    val name: String,
    val age: String,
    val type: String
) {
    val uppercaseName get() = name.toUpperCase(Locale.ROOT)
    val uppercaseAge get() = age.toUpperCase(Locale.ROOT)
    val uppercaseType get() = type.toUpperCase(Locale.ROOT)
}