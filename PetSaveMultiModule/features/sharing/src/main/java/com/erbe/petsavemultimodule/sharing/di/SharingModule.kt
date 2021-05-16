package com.erbe.petsavemultimodule.sharing.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.erbe.common.data.PetFinderAnimalRepository
import com.erbe.common.domain.repositories.AnimalRepository
import com.erbe.common.utils.CoroutineDispatchersProvider
import com.erbe.common.utils.DispatchersProvider
import com.erbe.petsavemultimodule.sharing.presentation.SharingFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.migration.DisableInstallInCheck
import dagger.multibindings.IntoMap

@Module
@DisableInstallInCheck
abstract class SharingModule {

    // These two are not scoped to SingletonComponent, so they can't be directly provided through
    // methods in SharingModuleDependencies.
    @Binds
    abstract fun bindDispatchersProvider(
        dispatchersProvider: CoroutineDispatchersProvider
    ): DispatchersProvider

    @Binds
    abstract fun bindRepository(repository: PetFinderAnimalRepository): AnimalRepository

    @Binds
    @IntoMap
    @ViewModelKey(SharingFragmentViewModel::class)
    abstract fun bindSharingFragmentViewModel(
        sharingFragmentViewModel: SharingFragmentViewModel
    ): ViewModel

    @Binds
    @Reusable
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}