package com.erbe.petsavemultimodule.animalsnearyou.presentation.animaldetails.model

data class UIAnimalDetailed(
    val id: Long,
    val name: String,
    val photo: String,
    val description: String,
    val sprayNeutered: Boolean,
    val specialNeeds: Boolean,
    val tags: List<String>,
    val phone: String
)