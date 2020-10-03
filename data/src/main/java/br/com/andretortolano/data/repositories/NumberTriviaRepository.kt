package br.com.andretortolano.data.repositories

import br.com.andretortolano.data.models.NumberTriviaModel
import br.com.andretortolano.data.sources.LocalSource
import br.com.andretortolano.data.sources.RemoteSource
import br.com.andretortolano.domain.entities.NumberTrivia
import br.com.andretortolano.domain.gateway.GatewayResponse
import br.com.andretortolano.domain.gateway.NumberTriviaGateway

class NumberTriviaRepository(private val remote: RemoteSource, private val local: LocalSource): NumberTriviaGateway {
    override fun getConcreteNumberTrivia(number: Int): GatewayResponse<NumberTrivia> {
        val entity = getNumberTriviaFromLocalSource(number) ?: getNumberTriviaFromRemoteSource(number)
        return GatewayResponse(entity)
    }

    private fun getNumberTriviaFromLocalSource(number: Int): NumberTrivia? {
        return local.getNumberTrivia(number)?.toEntity()
    }

    private fun getNumberTriviaFromRemoteSource(number: Int): NumberTrivia {
        return remote.getConcreteNumberTrivia(number).run {
            local.saveNumberTrivia(this)
            toEntity()
        }
    }

    override fun getRandomNumberTrivia(): GatewayResponse<NumberTrivia> {
        return remote.getRandomNumberTrivia().run {
            local.saveNumberTrivia(this)
            GatewayResponse(toEntity())
        }
    }

    private fun NumberTriviaModel.toEntity(): NumberTrivia = NumberTrivia(number, trivia)
}