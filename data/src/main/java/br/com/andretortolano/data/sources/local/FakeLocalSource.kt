package br.com.andretortolano.data.sources.local

import br.com.andretortolano.data.models.NumberTriviaModel

class FakeLocalSource: LocalSource {
    override fun getNumberTrivia(number: Int): NumberTriviaModel? {
        return null
    }

    override fun saveNumberTrivia(expectedNumberTrivia: NumberTriviaModel) {
        return
    }
}