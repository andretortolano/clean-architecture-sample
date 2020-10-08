package br.com.andretortolano.numbers_trivia.di

import br.com.andretortolano.data.DataModule
import br.com.andretortolano.domain.usecase.GetNumberTrivia
import br.com.andretortolano.domain.usecase.GetRandomNumberTrivia
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck

@Module
@DisableInstallInCheck
object FeatureModule {

    @Provides
    fun provideGetNumberTrivia(dataModule: DataModule) = GetNumberTrivia(dataModule.numberTriviaGateway)

    @Provides
    fun provideGetRandomNumberTrivia(dataModule: DataModule) = GetRandomNumberTrivia(dataModule.numberTriviaGateway)

}