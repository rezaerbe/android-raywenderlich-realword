package com.erbe.petsave.common.data.api.model.mappers

interface ApiMapper<E, D> {

    fun mapToDomain(apiEntity: E): D
}