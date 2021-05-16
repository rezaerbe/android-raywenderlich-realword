package com.erbe.petsavedesign.details.presentation

sealed class AnimalDetailsEvent {
    data class LoadAnimalDetails(val animalId: Long) : AnimalDetailsEvent()
    object AdoptAnimal : AnimalDetailsEvent()
}