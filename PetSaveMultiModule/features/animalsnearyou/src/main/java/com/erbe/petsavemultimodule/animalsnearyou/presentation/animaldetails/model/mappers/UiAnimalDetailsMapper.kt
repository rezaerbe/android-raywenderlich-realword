package com.erbe.petsavemultimodule.animalsnearyou.presentation.animaldetails.model.mappers

import com.erbe.petsavemultimodule.animalsnearyou.presentation.animaldetails.model.UIAnimalDetailed
import com.erbe.common.domain.model.animal.details.AnimalWithDetails
import com.erbe.common.presentation.model.mappers.UiMapper
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
            tags = input.tags,
            phone = input.details.organization.contact.phone
        )
    }
}