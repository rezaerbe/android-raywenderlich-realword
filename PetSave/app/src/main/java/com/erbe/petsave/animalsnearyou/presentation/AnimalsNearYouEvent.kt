package com.erbe.petsave.animalsnearyou.presentation

sealed class AnimalsNearYouEvent {
    object RequestInitialAnimalsList : AnimalsNearYouEvent()
    object RequestMoreAnimals : AnimalsNearYouEvent()
}