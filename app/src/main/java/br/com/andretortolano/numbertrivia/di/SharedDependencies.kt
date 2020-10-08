package br.com.andretortolano.numbertrivia.di

import br.com.andretortolano.numbertrivia.BuildConfig
import br.com.andretortolano.shared.di.SharedDependenciesProvider
import javax.inject.Inject

class SharedDependencies @Inject constructor() : SharedDependenciesProvider {
    override fun getNumberTriviaURL(): String = "http://numbersapi.com/"

    override fun isDebug(): Boolean = BuildConfig.DEBUG
}