package br.com.andretortolano.data.sources

import br.com.andretortolano.data.exception.RemoteException
import br.com.andretortolano.data.models.NumberTriviaModel
import br.com.andretortolano.data.sources.retrofit.NumberTriviaJson
import br.com.andretortolano.data.sources.retrofit.NumberTriviaRetrofit

class DefaultRemoteSource(private val api: NumberTriviaRetrofit) : RemoteSource {

    override fun getConcreteNumberTrivia(number: Int): NumberTriviaModel {
        api.getConcreteNumberTrivia(number).execute().run {
            return body().let {
                it?.toModel() ?: throw RemoteException.RemoteError
            }
        }
    }

    override fun getRandomNumberTrivia(): NumberTriviaModel {
        api.getRandomNumberTrivia().execute().run {
            return body().let {
                it?.toModel() ?: throw RemoteException.RemoteError
            }
        }
    }

    private fun NumberTriviaJson.toModel() = NumberTriviaModel(number = number, trivia = text)
}