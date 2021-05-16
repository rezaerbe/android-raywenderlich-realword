package com.erbe.petsavedesign.common.data.api.model.mappers

interface ApiMapper<E, D> {

    fun mapToDomain(apiEntity: E): D
}