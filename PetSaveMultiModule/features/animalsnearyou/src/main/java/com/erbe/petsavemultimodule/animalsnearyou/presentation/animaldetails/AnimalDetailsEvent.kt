package com.erbe.petsavemultimodule.animalsnearyou.presentation.animaldetails

sealed class AnimalDetailsEvent {
    data class LoadAnimalDetails(val animalId: Long) : AnimalDetailsEvent()
}