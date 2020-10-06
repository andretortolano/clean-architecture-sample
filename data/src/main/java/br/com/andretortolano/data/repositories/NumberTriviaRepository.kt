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
        return try {
            remote.getConcreteNumberTrivia(number).save()
        } catch (e: NoConnectivityException) {
            local.getNumberTrivia(number).toSuccessOrError(ErrorEntity.NoConnectivity)
        } catch (e: NotFoundException) {
            EntityResult.Error(ErrorEntity.NotFound)
        }
    }

    override fun getRandomNumberTrivia(): EntityResult<NumberTriviaEntity> {
        return try {
            remote.getRandomNumberTrivia().save()
        } catch (e: NoConnectivityException) {
            EntityResult.Error(ErrorEntity.NoConnectivity)
        } catch (e: NotFoundException) {
            EntityResult.Error(ErrorEntity.NotFound)
        }
    }

    private fun NumberTriviaModel.save(): EntityResult<NumberTriviaEntity> {
        local.saveNumberTrivia(this)
        return EntityResult.Success(toEntity())
    }

    private fun NumberTriviaModel?.toSuccessOrError(error: ErrorEntity): EntityResult<NumberTriviaEntity> {
        return if (this != null)
            EntityResult.Success(toEntity())
        else
            EntityResult.Error(error)
    }

    private fun NumberTriviaModel.toEntity(): NumberTriviaEntity = NumberTriviaEntity(number, trivia)
}