package br.com.andretortolano.domain.gateway

import br.com.andretortolano.domain.entity.NumberTriviaEntity
import br.com.andretortolano.domain.entity.EntityResult

interface NumberTriviaGateway {
    fun getNumberTrivia(number: Long): EntityResult<NumberTriviaEntity>
    fun getRandomNumberTrivia() : EntityResult<NumberTriviaEntity>
}