package br.com.andretortolano.shared.di

interface SharedDependenciesProvider {
    fun getNumberTriviaURL(): String

    fun isDebug(): Boolean
}