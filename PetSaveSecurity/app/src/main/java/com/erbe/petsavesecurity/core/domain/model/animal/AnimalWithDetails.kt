package com.erbe.petsavesecurity.core.domain.model.animal

import com.erbe.petsavesecurity.core.domain.model.organization.Organization
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
) {

    fun withNoDetails(): Animal {
        return Animal(id, name, type, media, tags, adoptionStatus, publishedAt)
    }

    data class Details(
        val description: String,
        val age: Age,
        val species: String,
        val breed: Breed,
        val colors: Colors,
        val gender: Gender,
        val size: Size,
        val coat: Coat,
        val healthDetails: HealthDetails,
        val habitatAdaptation: HabitatAdaptation,
        val organization: Organization
    ) {
        enum class Age {
            UNKNOWN,
            BABY,
            YOUNG,
            ADULT,
            SENIOR
        }

        data class Breed(
            val primary: String,
            val secondary: String
        ) {
            val mixed: Boolean
                get() = primary.isNotEmpty() && secondary.isNotEmpty()

            val unknown: Boolean
                get() = primary.isEmpty() && secondary.isEmpty()
        }

        data class Colors(
            val primary: String,
            val secondary: String,
            val tertiary: String
        )

        enum class Gender {
            UNKNOWN,
            FEMALE,
            MALE
        }

        enum class Size {
            UNKNOWN,
            SMALL,
            MEDIUM,
            LARGE,
            EXTRA_LARGE
        }

        enum class Coat {
            UNKNOWN,
            SHORT,
            MEDIUM,
            LONG,
            WIRE,
            HAIRLESS,
            CURLY
        }

        data class HealthDetails(
            val isSpayedOrNeutered: Boolean,
            val isDeclawed: Boolean,
            val hasSpecialNeeds: Boolean,
            val shotsAreCurrent: Boolean
        )

        data class HabitatAdaptation(
            val goodWithChildren: Boolean,
            val goodWithDogs: Boolean,
            val goodWithCats: Boolean
        )
    }
}