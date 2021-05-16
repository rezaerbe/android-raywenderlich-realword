package com.erbe.petsavedesign.common.data

import com.erbe.petsavedesign.common.data.api.PetFinderApi
import com.erbe.petsavedesign.common.data.api.model.mappers.ApiAnimalMapper
import com.erbe.petsavedesign.common.data.api.model.mappers.ApiPaginationMapper
import com.erbe.petsavedesign.common.data.cache.Cache
import com.erbe.petsavedesign.common.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.erbe.petsavedesign.common.data.cache.model.cachedorganization.CachedOrganization
import com.erbe.petsavedesign.common.domain.model.animal.Animal
import com.erbe.petsavedesign.common.domain.model.animal.details.Age
import com.erbe.petsavedesign.common.domain.model.animal.details.AnimalWithDetails
import com.erbe.petsavedesign.common.domain.model.pagination.PaginatedAnimals
import com.erbe.petsavedesign.common.domain.repositories.AnimalRepository
import com.erbe.petsavedesign.common.utils.DispatchersProvider
import com.erbe.petsavedesign.search.domain.model.SearchParameters
import com.erbe.petsavedesign.search.domain.model.SearchResults
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

@ActivityRetainedScoped
class PetFinderAnimalRepository @Inject constructor(
    private val api: PetFinderApi,
    private val cache: Cache,
    private val apiAnimalMapper: ApiAnimalMapper,
    private val apiPaginationMapper: ApiPaginationMapper,
    dispatchersProvider: DispatchersProvider
) : AnimalRepository {

    private val parentJob = SupervisorJob()
    private val repositoryScope = CoroutineScope(parentJob + dispatchersProvider.io())

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

    override fun getAnimal(animalId: Long): Single<AnimalWithDetails> {
        return cache.getAnimal(animalId)
            .flatMap { animal ->
                cache.getOrganization(animal.animal.organizationId)
                    .map {
                        animal.animal.toDomain(animal.photos, animal.videos, animal.tags, it)
                    }
            }
    }

    override suspend fun requestMoreAnimals(
        pageToLoad: Int,
        numberOfItems: Int
    ): PaginatedAnimals {
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
        return cache.searchAnimalsBy(
            searchParameters.uppercaseName,
            searchParameters.uppercaseAge,
            searchParameters.uppercaseType
        )
            .map { animalList ->
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
