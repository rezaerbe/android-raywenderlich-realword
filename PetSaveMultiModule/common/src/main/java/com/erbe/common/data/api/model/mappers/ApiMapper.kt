package com.erbe.common.data.api.model.mappers

interface ApiMapper<E, D> {

    fun mapToDomain(apiEntity: E): D
}