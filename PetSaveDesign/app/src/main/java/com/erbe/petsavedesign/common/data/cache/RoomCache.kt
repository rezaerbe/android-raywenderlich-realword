package com.erbe.petsavedesign.common.data.cache

import com.erbe.petsavedesign.common.data.cache.daos.AnimalsDao
import com.erbe.petsavedesign.common.data.cache.daos.OrganizationsDao
import com.erbe.petsavedesign.common.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.erbe.petsavedesign.common.data.cache.model.cachedorganization.CachedOrganization
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val animalsDao: AnimalsDao,
    private val organizationsDao: OrganizationsDao
) : Cache {

    override fun getNearbyAnimals(): Flowable<List<CachedAnimalAggregate>> {
        return animalsDao.getAllAnimals()
    }

    override fun getAnimal(animalId: Long): Single<CachedAnimalAggregate> {
        return animalsDao.getAnimal(animalId)
    }

    override fun storeOrganizations(organizations: List<CachedOrganization>) {
        organizationsDao.insert(organizations)
    }

    override fun getOrganization(organizationId: String): Single<CachedOrganization> {
        return organizationsDao.getOrganization(organizationId)
    }

    override fun storeNearbyAnimals(animals: List<CachedAnimalAggregate>) {
        animalsDao.insertAnimalsWithDetails(animals)
    }

    override suspend fun getAllTypes(): List<String> {
        return animalsDao.getAllTypes()
    }

    override fun searchAnimalsBy(
        nameOrBreed: String,
        age: String,
        type: String
    ): Flowable<List<CachedAnimalAggregate>> {
        return animalsDao.searchAnimalsBy(nameOrBreed, age, type)
    }
}