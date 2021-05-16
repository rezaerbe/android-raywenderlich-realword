package com.erbe.petsave.common.data.cache

import com.erbe.petsave.common.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.erbe.petsave.common.data.cache.model.cachedorganization.CachedOrganization
import io.reactivex.Flowable

interface Cache {
    suspend fun storeOrganizations(organizations: List<CachedOrganization>)
    fun getNearbyAnimals(): Flowable<List<CachedAnimalAggregate>>
    suspend fun storeNearbyAnimals(animals: List<CachedAnimalAggregate>)
    suspend fun getAllTypes(): List<String>
    fun searchAnimalsBy(
        name: String,
        age: String,
        type: String
    ): Flowable<List<CachedAnimalAggregate>>
}