package com.erbe.petsavesecurity.core.data.api.model.mappers

import com.erbe.petsavesecurity.core.data.api.model.ApiAnimal
import com.erbe.petsavesecurity.core.domain.model.animal.AdoptionStatus
import com.erbe.petsavesecurity.core.domain.model.animal.AnimalWithDetails
import com.erbe.petsavesecurity.core.domain.model.animal.Media
import com.erbe.petsavesecurity.core.domain.model.organization.Organization
import com.erbe.petsavesecurity.core.utils.DateTimeUtils
import java.util.*
import javax.inject.Inject

class ApiAnimalMapper @Inject constructor(
    private val apiBreedsMapper: ApiBreedsMapper,
    private val apiColorsMapper: ApiColorsMapper,
    private val apiHealthDetailsMapper: ApiHealthDetailsMapper,
    private val apiHabitatAdaptationMapper: ApiHabitatAdaptationMapper,
    private val apiPhotoMapper: ApiPhotoMapper,
    private val apiVideoMapper: ApiVideoMapper,
    private val apiContactMapper: ApiContactMapper
) : ApiMapper<ApiAnimal, AnimalWithDetails> {

    override fun mapToDomain(apiEntity: ApiAnimal): AnimalWithDetails {
        return AnimalWithDetails(
            id = apiEntity.id ?: throw MappingException("Animal ID cannot be null"),
            name = apiEntity.name.orEmpty(),
            type = apiEntity.type.orEmpty(),
            details = parseAnimalDetails(apiEntity),
            media = mapMedia(apiEntity),
            tags = apiEntity.tags.orEmpty().map { it.orEmpty() },
            adoptionStatus = parseAdoptionStatus(apiEntity.status),
            publishedAt = DateTimeUtils.parse(apiEntity.publishedAt.orEmpty()) // throws exception if empty
        )
    }

    private fun parseAnimalDetails(apiAnimal: ApiAnimal): AnimalWithDetails.Details {
        return AnimalWithDetails.Details(
            description = apiAnimal.description.orEmpty(),
            age = parseAge(apiAnimal.age),
            species = apiAnimal.species.orEmpty(),
            breed = apiBreedsMapper.mapToDomain(apiAnimal.breeds),
            colors = apiColorsMapper.mapToDomain(apiAnimal.colors),
            gender = parserGender(apiAnimal.gender),
            size = parseSize(apiAnimal.size),
            coat = parseCoat(apiAnimal.coat),
            healthDetails = apiHealthDetailsMapper.mapToDomain(apiAnimal.attributes),
            habitatAdaptation = apiHabitatAdaptationMapper.mapToDomain(apiAnimal.environment),
            organization = mapOrganization(apiAnimal)
        )
    }

    private fun parseAge(age: String?): AnimalWithDetails.Details.Age {
        if (age.isNullOrEmpty()) return AnimalWithDetails.Details.Age.UNKNOWN

        // will throw IllegalStateException if the string does not match any enum value
        return AnimalWithDetails.Details.Age.valueOf(age.toUpperCase(Locale.ROOT))
    }

    private fun parserGender(gender: String?): AnimalWithDetails.Details.Gender {
        if (gender.isNullOrEmpty()) return AnimalWithDetails.Details.Gender.UNKNOWN

        return AnimalWithDetails.Details.Gender.valueOf(gender.toUpperCase(Locale.ROOT))
    }

    private fun parseSize(size: String?): AnimalWithDetails.Details.Size {
        if (size.isNullOrEmpty()) return AnimalWithDetails.Details.Size.UNKNOWN

        return AnimalWithDetails.Details.Size.valueOf(
            size.replace(' ', '_').toUpperCase(Locale.ROOT)
        )
    }

    private fun parseCoat(coat: String?): AnimalWithDetails.Details.Coat {
        if (coat.isNullOrEmpty()) return AnimalWithDetails.Details.Coat.UNKNOWN

        return AnimalWithDetails.Details.Coat.valueOf(coat.toUpperCase(Locale.ROOT))
    }

    private fun mapMedia(apiAnimal: ApiAnimal): Media {
        return Media(
            photos = apiAnimal.photos?.map { apiPhotoMapper.mapToDomain(it) }.orEmpty(),
            videos = apiAnimal.videos?.map { apiVideoMapper.mapToDomain(it) }.orEmpty()
        )
    }

    private fun parseAdoptionStatus(status: String?): AdoptionStatus {
        if (status.isNullOrEmpty()) return AdoptionStatus.UNKNOWN

        return AdoptionStatus.valueOf(status.toUpperCase(Locale.ROOT))
    }

    private fun mapOrganization(apiAnimal: ApiAnimal): Organization {
        return Organization(
            id = apiAnimal.organizationId
                ?: throw MappingException("Organization ID cannot be null"),
            contact = apiContactMapper.mapToDomain(apiAnimal.contact),
            distance = apiAnimal.distance ?: -1f
        )
    }
}