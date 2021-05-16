package com.erbe.petsavedesign.common.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erbe.petsavedesign.common.data.cache.model.cachedorganization.CachedOrganization
import io.reactivex.Single

@Dao
interface OrganizationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(organizations: List<CachedOrganization>)

    @Query("SELECT * from organizations where organizationId is :id")
    fun getOrganization(id: String): Single<CachedOrganization>
}