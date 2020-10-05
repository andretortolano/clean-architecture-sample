package br.com.andretortolano.data.sources.remote

import br.com.andretortolano.data.exceptions.RemoteException
import br.com.andretortolano.data.models.NumberTriviaModel
import br.com.andretortolano.data.sources.remote.retrofit.NumberTriviaJson
import br.com.andretortolano.data.sources.remote.retrofit.NumberTriviaRetrofit

class RetrofitRemoteSource(private val api: NumberTriviaRetrofit) : RemoteSource {

    override fun getConcreteNumberTrivia(number: Int): NumberTriviaModel {
        api.getConcreteNumberTrivia(number).execute().run {
            return body().let {
                it?.toModel() ?: throw RemoteException.UnknownRemoteException
            }
        }
    }

    override fun getRandomNumberTrivia(): NumberTriviaModel {
        api.getRandomNumberTrivia().execute().run {
            return body().let {
                it?.toModel() ?: throw RemoteException.UnknownRemoteException
            }
        }
    }

    private fun NumberTriviaJson.toModel() = NumberTriviaModel(number = number, trivia = text, found = found)
}