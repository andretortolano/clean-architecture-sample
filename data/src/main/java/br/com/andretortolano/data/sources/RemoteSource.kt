package br.com.andretortolano.data.sources

import br.com.andretortolano.data.models.NumberTriviaModel

interface RemoteSource {
    fun getConcreteNumberTrivia(number: Int): NumberTriviaModel

    fun getRandomNumberTrivia(): NumberTriviaModel
}