package com.erbe.petsavesecurity.core.data.api.model.mappers

import com.erbe.petsavesecurity.core.data.api.model.ApiBreeds
import com.erbe.petsavesecurity.core.domain.model.animal.AnimalWithDetails
import javax.inject.Inject

class ApiBreedsMapper @Inject constructor() :
    ApiMapper<ApiBreeds?, AnimalWithDetails.Details.Breed> {

    override fun mapToDomain(apiEntity: ApiBreeds?): AnimalWithDetails.Details.Breed {
        return AnimalWithDetails.Details.Breed(
            primary = apiEntity?.primary.orEmpty(),
            secondary = apiEntity?.secondary.orEmpty()
        )
    }
}