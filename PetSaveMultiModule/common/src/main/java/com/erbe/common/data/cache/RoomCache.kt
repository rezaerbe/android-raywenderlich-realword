package com.erbe.common.data.cache

import com.erbe.common.data.cache.daos.AnimalsDao
import com.erbe.common.data.cache.daos.OrganizationsDao
import com.erbe.common.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.erbe.common.data.cache.model.cachedorganization.CachedOrganization
import io.reactivex.Flowable
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val animalsDao: AnimalsDao,
    private val organizationsDao: OrganizationsDao
) : Cache {

    override suspend fun storeOrganizations(organizations: List<CachedOrganization>) {
        organizationsDao.insert(organizations)
    }

    override suspend fun getOrganization(organizationId: String): CachedOrganization {
        return organizationsDao.getOrganization(organizationId)
    }

    override fun getNearbyAnimals(): Flowable<List<CachedAnimalAggregate>> {
        return animalsDao.getAllAnimals()
    }

    override suspend fun storeNearbyAnimals(animals: List<CachedAnimalAggregate>) {
        animalsDao.insertAnimalsWithDetails(animals)
    }

    override suspend fun getAnimal(animalId: Long): CachedAnimalAggregate {
        return animalsDao.getAnimal(animalId)
    }

    override suspend fun getAllTypes(): List<String> {
        return animalsDao.getAllTypes()
    }

    override fun searchAnimalsBy(
        name: String,
        age: String,
        type: String
    ): Flowable<List<CachedAnimalAggregate>> {
        return animalsDao.searchAnimalsBy(name, age, type)
    }
}