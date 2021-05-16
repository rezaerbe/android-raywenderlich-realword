package com.erbe.common.domain.repositories

import com.erbe.common.domain.model.animal.Animal
import com.erbe.common.domain.model.animal.details.Age
import com.erbe.common.domain.model.animal.details.AnimalWithDetails
import com.erbe.common.domain.model.pagination.PaginatedAnimals
import com.erbe.common.domain.model.search.SearchParameters
import com.erbe.common.domain.model.search.SearchResults
import io.reactivex.Flowable

interface AnimalRepository {
    fun getAnimals(): Flowable<List<Animal>>
    suspend fun requestMoreAnimals(pageToLoad: Int, numberOfItems: Int): PaginatedAnimals
    suspend fun storeAnimals(animals: List<AnimalWithDetails>)
    suspend fun getAnimal(animalId: Long): AnimalWithDetails

    suspend fun getAnimalTypes(): List<String>
    fun getAnimalAges(): List<Age>
    fun searchCachedAnimalsBy(searchParameters: SearchParameters): Flowable<SearchResults>
    suspend fun searchAnimalsRemotely(
        pageToLoad: Int,
        searchParameters: SearchParameters,
        numberOfItems: Int
    ): PaginatedAnimals

    suspend fun storeOnboardingData(postcode: String, distance: Int)
    suspend fun onboardingIsComplete(): Boolean
}