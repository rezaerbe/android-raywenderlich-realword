package com.erbe.petsavesecurity.core.data.cache.model.cachedanimal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class CachedTag(
    @PrimaryKey(autoGenerate = false)
    val tag: String
)