package com.erbe.petsavemultimodule.sharing.presentation

sealed class SharingEvent {
    data class GetAnimalToShare(val animalId: Long) : SharingEvent()
}