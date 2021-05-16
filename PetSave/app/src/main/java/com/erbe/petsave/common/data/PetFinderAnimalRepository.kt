package com.erbe.petsave.common.data

import com.erbe.petsave.common.data.api.PetFinderApi
import com.erbe.petsave.common.data.api.model.mappers.ApiAnimalMapper
import com.erbe.petsave.common.data.api.model.mappers.ApiPaginationMapper
import com.erbe.petsave.common.data.cache.Cache
import com.erbe.petsave.common.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.erbe.petsave.common.data.cache.model.cachedorganization.CachedOrganization
import com.erbe.petsave.common.domain.model.NetworkException
import com.erbe.petsave.common.domain.model.animal.Animal
import com.erbe.petsave.common.domain.model.animal.details.Age
import com.erbe.petsave.common.domain.model.animal.details.AnimalWithDetails
import com.erbe.petsave.common.domain.model.pagination.PaginatedAnimals
import com.erbe.petsave.common.domain.repositories.AnimalRepository
import com.erbe.petsave.search.domain.model.SearchParameters
import com.erbe.petsave.search.domain.model.SearchResults
import io.reactivex.Flowable
import retrofit2.HttpException
import javax.inject.Inject

class PetFinderAnimalRepository @Inject constructor(
    private val api: PetFinderApi,
    private val cache: Cache,
    private val apiAnimalMapper: ApiAnimalMapper,
    private val apiPaginationMapper: ApiPaginationMapper
) : AnimalRepository {

    // fetch these from shared preferences, after storing them in onboarding screen
    private val postcode = "07097"
    private val maxDistanceMiles = 100

    override fun getAnimals(): Flowable<List<Animal>> {
        return cache.getNearbyAnimals()
            .distinctUntilChanged()
            .map { animalList ->
                animalList.map { it.animal.toAnimalDomain(it.photos, it.videos, it.tags) }
            }
    }

    override suspend fun requestMoreAnimals(pageToLoad: Int, numberOfItems: Int): PaginatedAnimals {
        try {
            val (apiAnimals, apiPagination) = api.getNearbyAnimals(
                pageToLoad,
                numberOfItems,
                postcode,
                maxDistanceMiles
            )

            return PaginatedAnimals(
                apiAnimals?.map { apiAnimalMapper.mapToDomain(it) }.orEmpty(),
                apiPaginationMapper.mapToDomain(apiPagination)
            )
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun storeAnimals(animals: List<AnimalWithDetails>) {
        // Organizations have a 1-to-many relation with animals, so we need to insert them first in
        // order for Room not to complain about foreign keys being invalid (since we have the
        // organizationId as a foreign key in the animals table)
        val organizations = animals.map { CachedOrganization.fromDomain(it.details.organization) }

        cache.storeOrganizations(organizations)
        cache.storeNearbyAnimals(animals.map { CachedAnimalAggregate.fromDomain(it) })
    }

    override suspend fun getAnimalTypes(): List<String> {
        return cache.getAllTypes()
    }

    override fun getAnimalAges(): List<Age> {
        return Age.values().toList()
    }

    override fun searchCachedAnimalsBy(searchParameters: SearchParameters): Flowable<SearchResults> {
        val (name, age, type) = searchParameters

        return cache.searchAnimalsBy(name, age, type)
            .distinctUntilChanged().map { animalList ->
                animalList.map { it.animal.toAnimalDomain(it.photos, it.videos, it.tags) }
            }
            .map { SearchResults(it, searchParameters) }
    }

    override suspend fun searchAnimalsRemotely(
        pageToLoad: Int,
        searchParameters: SearchParameters,
        numberOfItems: Int
    ): PaginatedAnimals {

        val (apiAnimals, apiPagination) = api.searchAnimalsBy(
            searchParameters.name,
            searchParameters.age,
            searchParameters.type,
            pageToLoad,
            numberOfItems,
            postcode,
            maxDistanceMiles
        )

        return PaginatedAnimals(
            apiAnimals?.map { apiAnimalMapper.mapToDomain(it) }.orEmpty(),
            apiPaginationMapper.mapToDomain(apiPagination)
        )
    }
}