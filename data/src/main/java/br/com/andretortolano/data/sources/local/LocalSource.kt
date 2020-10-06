package br.com.andretortolano.data.sources.local

import br.com.andretortolano.data.models.NumberTriviaModel

interface LocalSource {
    fun getNumberTrivia(number: Long): NumberTriviaModel?

    fun saveNumberTrivia(expectedNumberTrivia: NumberTriviaModel)
}