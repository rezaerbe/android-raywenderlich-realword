package com.erbe.petsavesecurity.core.di

import com.erbe.petsavesecurity.core.utils.CoroutineDispatchersProvider
import com.erbe.petsavesecurity.core.utils.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class ApplicationModule {

    @Binds
    abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider):
            DispatchersProvider
}