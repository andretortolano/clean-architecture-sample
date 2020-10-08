package br.com.andretortolano.numbertrivia.di

import br.com.andretortolano.shared.di.SharedDependenciesProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindSharedDependenciesProvider(provider: SharedDependencies) : SharedDependenciesProvider
}