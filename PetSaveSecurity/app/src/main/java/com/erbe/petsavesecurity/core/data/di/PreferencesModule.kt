package com.erbe.petsavesecurity.core.data.di

import com.erbe.petsavesecurity.core.data.preferences.PetSavePreferences
import com.erbe.petsavesecurity.core.data.preferences.Preferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun providePreferences(preferences: PetSavePreferences): Preferences
}