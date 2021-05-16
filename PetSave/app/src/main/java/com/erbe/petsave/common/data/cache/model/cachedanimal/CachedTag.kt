package com.erbe.petsave.common.data.cache.model.cachedanimal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class CachedTag(
    @PrimaryKey
    val tag: String
)