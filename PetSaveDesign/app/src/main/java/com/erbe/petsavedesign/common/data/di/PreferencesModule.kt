package com.erbe.petsavedesign.common.data.di

import com.erbe.petsavedesign.common.data.preferences.PetSavePreferences
import com.erbe.petsavedesign.common.data.preferences.Preferences
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