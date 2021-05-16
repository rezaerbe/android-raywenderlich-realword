package com.erbe.common.di

import com.erbe.common.data.PetFinderAnimalRepository
import com.erbe.common.domain.repositories.AnimalRepository
import com.erbe.common.utils.CoroutineDispatchersProvider
import com.erbe.common.utils.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.disposables.CompositeDisposable

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindAnimalRepository(repository: PetFinderAnimalRepository): AnimalRepository

    @Binds
    abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider): DispatchersProvider

    companion object {
        @Provides
        fun provideCompositeDisposable() = CompositeDisposable()
    }
}