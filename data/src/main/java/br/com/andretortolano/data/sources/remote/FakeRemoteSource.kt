package br.com.andretortolano.data.sources.remote

import br.com.andretortolano.data.models.NumberTriviaModel

class FakeRemoteSource: RemoteSource {
    override fun getConcreteNumberTrivia(number: Int): NumberTriviaModel {
        return NumberTriviaModel(number.toBigInteger(), "test trivia for number $number", true)
    }

    override fun getRandomNumberTrivia(): NumberTriviaModel {
        return NumberTriviaModel(1234.toBigInteger(), "test random trivia", true)
    }
}