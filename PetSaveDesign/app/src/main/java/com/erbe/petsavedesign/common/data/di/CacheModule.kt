package com.erbe.petsavedesign.common.data.di

import android.content.Context
import androidx.room.Room
import com.erbe.petsavedesign.common.data.cache.Cache
import com.erbe.petsavedesign.common.data.cache.PetSaveDatabase
import com.erbe.petsavedesign.common.data.cache.RoomCache
import com.erbe.petsavedesign.common.data.cache.daos.AnimalsDao
import com.erbe.petsavedesign.common.data.cache.daos.OrganizationsDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): Cache

    companion object {

        @Provides
        fun provideDatabase(@ApplicationContext context: Context): PetSaveDatabase {
            return Room.databaseBuilder(context, PetSaveDatabase::class.java, "petsave.db")
                .build()
        }

        @Provides
        fun provideAnimalsDao(petSaveDatabase: PetSaveDatabase): AnimalsDao =
            petSaveDatabase.animalsDao()

        @Provides
        fun provideOrganizationsDao(petSaveDatabase: PetSaveDatabase): OrganizationsDao =
            petSaveDatabase.organizationsDao()
    }
}