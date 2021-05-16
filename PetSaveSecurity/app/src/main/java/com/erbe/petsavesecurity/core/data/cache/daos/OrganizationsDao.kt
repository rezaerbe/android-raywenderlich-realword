package com.erbe.petsavesecurity.core.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.erbe.petsavesecurity.core.data.cache.model.cachedorganization.CachedOrganization

@Dao
interface OrganizationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(organizations: List<CachedOrganization>)
}