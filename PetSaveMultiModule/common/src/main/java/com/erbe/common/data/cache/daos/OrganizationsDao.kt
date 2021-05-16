package com.erbe.common.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erbe.common.data.cache.model.cachedorganization.CachedOrganization

@Dao
interface OrganizationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(organizations: List<CachedOrganization>)

    @Query("SELECT * FROM organizations WHERE organizationId IS :organizationId")
    suspend fun getOrganization(organizationId: String): CachedOrganization
}