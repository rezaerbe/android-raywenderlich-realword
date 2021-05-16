package com.erbe.petsavesecurity.core.presentation.model.mappers

interface UiMapper<E, V> {

    fun mapToView(input: E): V
}