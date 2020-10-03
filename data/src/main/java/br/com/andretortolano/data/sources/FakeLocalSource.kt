package br.com.andretortolano.data.sources

import br.com.andretortolano.data.models.NumberTriviaModel

class FakeLocalSource: LocalSource {
    override fun getNumberTrivia(number: Int): NumberTriviaModel? {
        return null
    }

    override fun saveNumberTrivia(expectedNumberTrivia: NumberTriviaModel) {
        return
    }
}