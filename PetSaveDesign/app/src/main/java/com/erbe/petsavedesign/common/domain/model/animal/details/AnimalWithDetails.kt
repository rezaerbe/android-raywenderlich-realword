package com.erbe.petsavedesign.common.domain.model.animal.details

import com.erbe.petsavedesign.common.domain.model.animal.AdoptionStatus
import com.erbe.petsavedesign.common.domain.model.animal.Media
import org.threeten.bp.LocalDateTime

data class AnimalWithDetails(
    val id: Long,
    val name: String,
    val type: String,
    val details: Details,
    val media: Media,
    val tags: List<String>,
    val adoptionStatus: AdoptionStatus,
    val publishedAt: LocalDateTime
)