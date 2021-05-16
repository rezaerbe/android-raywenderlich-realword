package com.erbe.common.data.di

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.erbe.common.data.cache.Cache
import com.erbe.common.data.cache.PetSaveDatabase
import com.erbe.common.data.cache.RoomCache
import com.erbe.common.data.cache.daos.AnimalsDao
import com.erbe.common.data.cache.daos.OrganizationsDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TestCacheModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): Cache

    companion object {

        @Provides
        @Singleton
        fun provideRoomDatabase(): PetSaveDatabase {
            return Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                PetSaveDatabase::class.java
            )
                .allowMainThreadQueries()
                .build()
        }

        @Provides
        fun provideAnimalsDao(
            petSaveDatabase: PetSaveDatabase
        ): AnimalsDao = petSaveDatabase.animalsDao()

        @Provides
        fun provideOrganizationsDao(petSaveDatabase: PetSaveDatabase): OrganizationsDao =
            petSaveDatabase.organizationsDao()
    }
}