package com.erbe.common.data.di

import com.erbe.common.data.preferences.FakePreferences
import com.erbe.common.data.preferences.Preferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TestPreferencesModule {

    @Binds
    abstract fun providePreferences(preferences: FakePreferences): Preferences
}