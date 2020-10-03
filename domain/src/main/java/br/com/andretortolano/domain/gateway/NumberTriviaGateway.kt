package br.com.andretortolano.domain.gateway

import br.com.andretortolano.domain.entities.NumberTrivia

interface NumberTriviaGateway {
    fun getConcreteNumberTrivia(number: Int): GatewayResponse<NumberTrivia>

    fun getRandomNumberTrivia(): GatewayResponse<NumberTrivia>
}