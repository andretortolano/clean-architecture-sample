package br.com.andretortolano.data.sources.local

import br.com.andretortolano.data.models.NumberTriviaModel

class FakeLocalSource: LocalSource {
    override fun getNumberTrivia(number: Long): NumberTriviaModel? {
        return null
    }

    override fun saveNumberTrivia(numberTrivia: NumberTriviaModel) {
        return
    }
}