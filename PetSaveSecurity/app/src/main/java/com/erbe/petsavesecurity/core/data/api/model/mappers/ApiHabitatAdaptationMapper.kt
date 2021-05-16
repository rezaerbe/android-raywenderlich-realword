package com.erbe.petsavesecurity.core.data.api.model.mappers

import com.erbe.petsavesecurity.core.data.api.model.ApiEnvironment
import com.erbe.petsavesecurity.core.domain.model.animal.AnimalWithDetails
import javax.inject.Inject

class ApiHabitatAdaptationMapper @Inject constructor() :
    ApiMapper<ApiEnvironment?, AnimalWithDetails.Details.HabitatAdaptation> {

    override fun mapToDomain(apiEntity: ApiEnvironment?): AnimalWithDetails.Details.HabitatAdaptation {
        return AnimalWithDetails.Details.HabitatAdaptation(
            goodWithChildren = apiEntity?.children ?: true,
            goodWithDogs = apiEntity?.dogs ?: true,
            goodWithCats = apiEntity?.cats ?: true
        )
    }
}