package com.erbe.petsave.common.data.api.model.mappers

import com.erbe.petsave.common.data.api.model.ApiAttributes
import com.erbe.petsave.common.domain.model.animal.details.HealthDetails
import javax.inject.Inject

class ApiHealthDetailsMapper @Inject constructor() : ApiMapper<ApiAttributes?, HealthDetails> {

    override fun mapToDomain(apiEntity: ApiAttributes?): HealthDetails {
        return HealthDetails(
            isSpayedOrNeutered = apiEntity?.spayedNeutered ?: false,
            isDeclawed = apiEntity?.declawed ?: false,
            hasSpecialNeeds = apiEntity?.specialNeeds ?: false,
            shotsAreCurrent = apiEntity?.shotsCurrent ?: false
        )
    }
}