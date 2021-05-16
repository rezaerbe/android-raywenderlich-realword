package com.erbe.petsavedesign.common.di

import com.erbe.petsavedesign.common.utils.CoroutineDispatchersProvider
import com.erbe.petsavedesign.common.utils.DispatchersProvider
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