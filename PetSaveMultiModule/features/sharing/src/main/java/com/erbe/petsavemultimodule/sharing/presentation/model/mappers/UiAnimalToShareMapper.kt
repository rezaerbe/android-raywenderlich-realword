package com.erbe.petsavemultimodule.sharing.presentation.model.mappers

import com.erbe.common.domain.model.animal.details.AnimalWithDetails
import com.erbe.common.presentation.model.mappers.UiMapper
import com.erbe.petsavemultimodule.sharing.presentation.model.UIAnimalToShare
import javax.inject.Inject

class UiAnimalToShareMapper @Inject constructor() : UiMapper<AnimalWithDetails, UIAnimalToShare> {

    override fun mapToView(input: AnimalWithDetails): UIAnimalToShare {
        val message = createMessage(input)

        return UIAnimalToShare(input.media.getFirstSmallestAvailablePhoto(), message)
    }

    private fun createMessage(input: AnimalWithDetails): String {
        val contact = input.organizationContact
        val formattedAddress = contact.formattedAddress
        val formattedContactInfo = contact.formattedContactInfo

        return "${input.description}\n\nOrganization info:\n$formattedAddress\n\n$formattedContactInfo"
    }
}