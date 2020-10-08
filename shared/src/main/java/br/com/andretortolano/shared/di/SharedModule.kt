package br.com.andretortolano.shared.di

import android.content.Context
import br.com.andretortolano.data.DataModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object SharedModule {

    @Provides
    @Singleton
    fun provideDataModule(
        @ApplicationContext context: Context,
        dependenciesProvider: SharedDependenciesProvider
    ): DataModule {
        return DataModule.Builder(context)
            .setNumberTriviaApi(dependenciesProvider.getNumberTriviaURL())
            .build(dependenciesProvider.isDebug())
    }

}