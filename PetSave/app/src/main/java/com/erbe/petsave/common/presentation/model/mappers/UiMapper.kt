package com.erbe.petsave.common.presentation.model.mappers

interface UiMapper<E, V> {

    fun mapToView(input: E): V
}