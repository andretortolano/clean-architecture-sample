package br.com.andretortolano.data.repositories

import br.com.andretortolano.data.exceptions.NoConnectivityException
import br.com.andretortolano.data.exceptions.NotFoundException
import br.com.andretortolano.data.models.NumberTriviaModel
import br.com.andretortolano.data.sources.local.LocalSource
import br.com.andretortolano.data.sources.remote.RemoteSource
import br.com.andretortolano.domain.entity.EntityResult
import br.com.andretortolano.domain.entity.ErrorEntity
import br.com.andretortolano.domain.entity.NumberTriviaEntity
import br.com.andretortolano.domain.gateway.NumberTriviaGateway

class NumberTriviaRepository(private val remote: RemoteSource, private val local: LocalSource) : NumberTriviaGateway {

    override fun getNumberTrivia(number: Long): EntityResult<NumberTriviaEntity> {
        return getNumberTriviaFromLocal(number) ?: getNumberTriviaFromRemote(number)
    }

    private fun getNumberTriviaFromLocal(number: Long): EntityResult<NumberTriviaEntity>? {
        return local.getNumberTrivia(number)?.let {
            EntityResult.Success(it.toEntity())
        }
    }

    private fun getNumberTriviaFromRemote(number: Long): EntityResult<NumberTriviaEntity> {
        return try {
            remote.getConcreteNumberTrivia(number).run {
                local.saveNumberTrivia(this)
                EntityResult.Success(toEntity())
            }
        } catch (e: NoConnectivityException) {
            EntityResult.Error(ErrorEntity.NoConnectivity)
        } catch (e: NotFoundException) {
            EntityResult.Error(ErrorEntity.NotFound)
        }
    }

    override fun getRandomNumberTrivia(): EntityResult<NumberTriviaEntity> {
        return getRandomNumberTriviaFromRemote()
    }

    private fun getRandomNumberTriviaFromRemote(): EntityResult<NumberTriviaEntity> {
        return try {
            remote.getRandomNumberTrivia().run {
                local.saveNumberTrivia(this)
                EntityResult.Success(toEntity())
            }
        } catch (e: NoConnectivityException) {
            EntityResult.Error(ErrorEntity.NoConnectivity)
        } catch (e: NotFoundException) {
            EntityResult.Error(ErrorEntity.NotFound)
        }
    }

    private fun NumberTriviaModel.toEntity(): NumberTriviaEntity = NumberTriviaEntity(number, trivia)
}