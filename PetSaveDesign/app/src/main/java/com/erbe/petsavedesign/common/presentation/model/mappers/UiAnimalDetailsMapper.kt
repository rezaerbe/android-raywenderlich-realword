package com.erbe.petsavedesign.common.presentation.model.mappers

import com.erbe.petsavedesign.common.domain.model.animal.details.AnimalWithDetails
import com.erbe.petsavedesign.common.presentation.model.UIAnimalDetailed
import javax.inject.Inject

class UiAnimalDetailsMapper @Inject constructor() : UiMapper<AnimalWithDetails, UIAnimalDetailed> {

    override fun mapToView(input: AnimalWithDetails): UIAnimalDetailed {
        return UIAnimalDetailed(
            id = input.id,
            name = input.name,
            photo = input.media.getFirstSmallestAvailablePhoto(),
            description = input.details.description,
            sprayNeutered = input.details.healthDetails.isSpayedOrNeutered,
            specialNeeds = input.details.healthDetails.hasSpecialNeeds,
            declawed = input.details.healthDetails.isDeclawed,
            shotsCurrent = input.details.healthDetails.shotsAreCurrent,
            tags = input.tags,
            phone = input.details.organization.contact.phone
        )
    }
}