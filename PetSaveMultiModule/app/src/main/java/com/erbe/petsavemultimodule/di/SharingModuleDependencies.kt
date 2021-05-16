package com.erbe.petsavemultimodule.di

import com.erbe.common.data.api.PetFinderApi
import com.erbe.common.data.cache.Cache
import com.erbe.common.data.preferences.Preferences
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SharingModuleDependencies {
    fun petFinderApi(): PetFinderApi
    fun cache(): Cache
    fun preferences(): Preferences
}