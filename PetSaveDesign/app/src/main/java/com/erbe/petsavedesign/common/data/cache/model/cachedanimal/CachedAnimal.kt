package com.erbe.petsavedesign.common.data.cache.model.cachedanimal

import com.erbe.petsavedesign.common.domain.model.animal.AdoptionStatus
import com.erbe.petsavedesign.common.domain.model.animal.Animal
import com.erbe.petsavedesign.common.domain.model.animal.Media
import com.erbe.petsavedesign.common.utils.DateTimeUtils

data class CachedAnimal(
    val animalId: Long,
    val name: String,
    val type: String,
    val adoptionStatus: String,
    val publishedAt: String
) {

    companion object {
        fun fromDomain(animal: Animal): CachedAnimal {
            return CachedAnimal(
                animal.id,
                animal.name,
                animal.type,
                animal.adoptionStatus.toString(),
                animal.publishedAt.toString()
            )
        }
    }

    fun toDomain(
        photos: List<CachedPhoto>,
        videos: List<CachedVideo>,
        tags: List<CachedTag>
    ): Animal {
        return Animal(
            animalId,
            name,
            type,
            Media(
                photos = photos.map { it.toDomain() },
                videos = videos.map { it.toDomain() }
            ),
            tags.map { it.tag },
            AdoptionStatus.valueOf(adoptionStatus),
            DateTimeUtils.parse(publishedAt)
        )
    }
}