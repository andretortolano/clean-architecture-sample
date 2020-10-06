package br.com.andretortolano.data.sources.remote

import br.com.andretortolano.data.models.NumberTriviaModel

class FakeRemoteSource: RemoteSource {
    override fun getConcreteNumberTrivia(number: Long): NumberTriviaModel {
        return NumberTriviaModel(number, "test trivia for number $number")
    }

    override fun getRandomNumberTrivia(): NumberTriviaModel {
        return NumberTriviaModel(1234, "test random trivia")
    }
}