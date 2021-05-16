package com.erbe.petsavesecurity.core.data.api.model.mappers

import com.erbe.petsavesecurity.core.data.api.model.ApiAttributes
import com.erbe.petsavesecurity.core.domain.model.animal.AnimalWithDetails
import javax.inject.Inject

class ApiHealthDetailsMapper @Inject constructor() :
    ApiMapper<ApiAttributes?, AnimalWithDetails.Details.HealthDetails> {

    override fun mapToDomain(apiEntity: ApiAttributes?): AnimalWithDetails.Details.HealthDetails {
        return AnimalWithDetails.Details.HealthDetails(
            isSpayedOrNeutered = apiEntity?.spayedNeutered ?: false,
            isDeclawed = apiEntity?.declawed ?: false,
            hasSpecialNeeds = apiEntity?.specialNeeds ?: false,
            shotsAreCurrent = apiEntity?.shotsCurrent ?: false
        )
    }
}