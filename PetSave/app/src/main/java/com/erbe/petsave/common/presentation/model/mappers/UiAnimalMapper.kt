package com.erbe.petsave.common.presentation.model.mappers

import com.erbe.petsave.common.domain.model.animal.Animal
import com.erbe.petsave.common.presentation.model.UIAnimal
import javax.inject.Inject

class UiAnimalMapper @Inject constructor() : UiMapper<Animal, UIAnimal> {

    override fun mapToView(input: Animal): UIAnimal {
        return UIAnimal(
            id = input.id,
            name = input.name,
            photo = input.media.getFirstSmallestAvailablePhoto()
        )
    }
}