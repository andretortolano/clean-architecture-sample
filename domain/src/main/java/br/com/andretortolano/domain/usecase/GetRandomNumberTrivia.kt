package br.com.andretortolano.domain.usecase

import br.com.andretortolano.domain.entity.NumberTriviaEntity
import br.com.andretortolano.domain.entity.EntityResult
import br.com.andretortolano.domain.gateway.NumberTriviaGateway

class GetRandomNumberTrivia(private val gateway: NumberTriviaGateway) {
    operator fun invoke(): EntityResult<NumberTriviaEntity> {
        return gateway.getRandomNumberTrivia()
    }
}