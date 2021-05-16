package com.erbe.petsavedesign.common.data.cache.model.cachedorganization

import androidx.room.Embedded
import androidx.room.Relation
import com.erbe.petsavedesign.common.data.cache.model.cachedanimal.CachedAnimalAggregate
import com.erbe.petsavedesign.common.data.cache.model.cachedanimal.CachedAnimalWithDetails

data class CachedOrganizationWithAnimals(
    @Embedded val organization: CachedOrganization,
    @Relation(
        entity = CachedAnimalWithDetails::class,
        parentColumn = "id",
        entityColumn = "organizationId"
    )
    val animals: List<CachedAnimalAggregate>
)