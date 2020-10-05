package br.com.andretortolano.domain.gateway

import br.com.andretortolano.domain.entity.NumberTriviaEntity

interface NumberTriviaGateway {
    fun getNumberTrivia(number: Int): NumberTriviaEntity
    fun getRandomNumberTrivia() : NumberTriviaEntity
}