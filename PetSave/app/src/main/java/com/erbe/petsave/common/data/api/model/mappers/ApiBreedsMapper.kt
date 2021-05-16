package com.erbe.petsave.common.data.api.model.mappers

import com.erbe.petsave.common.data.api.model.ApiBreeds
import com.erbe.petsave.common.domain.model.animal.details.Breed
import javax.inject.Inject

class ApiBreedsMapper @Inject constructor() : ApiMapper<ApiBreeds?, Breed> {

    override fun mapToDomain(apiEntity: ApiBreeds?): Breed {
        return Breed(
            primary = apiEntity?.primary.orEmpty(),
            secondary = apiEntity?.secondary.orEmpty()
        )
    }
}