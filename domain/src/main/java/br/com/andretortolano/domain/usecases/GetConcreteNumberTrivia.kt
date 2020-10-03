package br.com.andretortolano.domain.usecases

import br.com.andretortolano.domain.entities.NumberTrivia
import br.com.andretortolano.domain.gateway.GatewayResponse
import br.com.andretortolano.domain.gateway.NumberTriviaGateway

class GetConcreteNumberTrivia(private val numberTriviaGateway: NumberTriviaGateway) {

    sealed class GetConcreteNumberTriviaResponse {
        data class Success(val numberTrivia: NumberTrivia) : GetConcreteNumberTriviaResponse()
        object NoTriviaFoundError : GetConcreteNumberTriviaResponse()
    }

    operator fun invoke(number: Int): GetConcreteNumberTriviaResponse {
        return numberTriviaGateway.getConcreteNumberTrivia(number).toResponse()
    }

    private fun GatewayResponse<NumberTrivia>.toResponse(): GetConcreteNumberTriviaResponse {
        return if(value != null) {
            GetConcreteNumberTriviaResponse.Success(value)
        } else {
            GetConcreteNumberTriviaResponse.NoTriviaFoundError
        }
    }
}
