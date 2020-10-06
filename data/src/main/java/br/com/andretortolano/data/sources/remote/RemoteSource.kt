package br.com.andretortolano.data.sources.remote

import br.com.andretortolano.data.models.NumberTriviaModel

interface RemoteSource {
    fun getConcreteNumberTrivia(number: Long): NumberTriviaModel

    fun getRandomNumberTrivia(): NumberTriviaModel
}