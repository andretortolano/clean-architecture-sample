package br.com.andretortolano.domain.usecase

import br.com.andretortolano.domain.entity.NumberTriviaEntity
import br.com.andretortolano.domain.entity.EntityResult
import br.com.andretortolano.domain.gateway.NumberTriviaGateway

class GetNumberTrivia(private val gateway: NumberTriviaGateway) {

    operator fun invoke(number: Long): EntityResult<NumberTriviaEntity> {
        return gateway.getNumberTrivia(number)
    }
}