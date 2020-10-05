package br.com.andretortolano.data.repositories

import br.com.andretortolano.data.models.NumberTriviaModel
import br.com.andretortolano.data.sources.local.LocalSource
import br.com.andretortolano.data.sources.remote.RemoteSource
import br.com.andretortolano.domain.entity.NumberTriviaEntity
import br.com.andretortolano.domain.gateway.NumberTriviaGateway

class NumberTriviaRepository(private val remote: RemoteSource, private val local: LocalSource) : NumberTriviaGateway {
    override fun getNumberTrivia(number: Int): NumberTriviaEntity {
        return getNumberTriviaFromLocal(number) ?: getNumberTriviaFromRemote(number)
    }

    private fun getNumberTriviaFromLocal(number: Int): NumberTriviaEntity? {
        return local.getNumberTrivia(number)?.toEntity()
    }

    private fun getNumberTriviaFromRemote(number: Int): NumberTriviaEntity {
        return remote.getConcreteNumberTrivia(number).run {
            local.saveNumberTrivia(this)
            toEntity()
        }
    }

    override fun getRandomNumberTrivia(): NumberTriviaEntity {
        return getRandomNumberTriviaFromRemote()
    }

    private fun getRandomNumberTriviaFromRemote(): NumberTriviaEntity {
        return remote.getRandomNumberTrivia().run {
            local.saveNumberTrivia(this)
            toEntity()
        }
    }

    private fun NumberTriviaModel.toEntity(): NumberTriviaEntity = NumberTriviaEntity(number, trivia, found)
}