package br.com.andretortolano.domain.usecases

import br.com.andretortolano.domain.entities.NumberTrivia
import br.com.andretortolano.domain.gateway.GatewayResponse
import br.com.andretortolano.domain.gateway.NumberTriviaGateway

class GetRandomNumberTrivia(private val numberTriviaGateway: NumberTriviaGateway) {

    sealed class GetRandomNumberTriviaResponse {
        data class Success(val numberTrivia: NumberTrivia) : GetRandomNumberTriviaResponse()
        object NoTriviaFoundError : GetRandomNumberTriviaResponse()
    }

    operator fun invoke(): GetRandomNumberTriviaResponse {
        return numberTriviaGateway.getRandomNumberTrivia().toResponse()
    }

    private fun GatewayResponse<NumberTrivia>.toResponse(): GetRandomNumberTriviaResponse {
        return if (value != null) {
            GetRandomNumberTriviaResponse.Success(value)
        } else {
            GetRandomNumberTriviaResponse.NoTriviaFoundError
        }
    }
}