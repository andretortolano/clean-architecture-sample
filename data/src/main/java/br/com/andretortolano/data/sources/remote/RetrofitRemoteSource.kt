package br.com.andretortolano.data.sources.remote

import br.com.andretortolano.data.exceptions.NotFoundException
import br.com.andretortolano.data.models.NumberTriviaModel
import br.com.andretortolano.data.sources.remote.retrofit.NumberTriviaJson
import br.com.andretortolano.data.sources.remote.retrofit.NumberTriviaRetrofit

internal class RetrofitRemoteSource(private val api: NumberTriviaRetrofit) : RemoteSource {

    override fun getConcreteNumberTrivia(number: Long): NumberTriviaModel {
        api.getConcreteNumberTrivia(number).execute().run {
            body()?.let {
                if (it.found) {
                    return it.toModel()
                }
            }
            throw NotFoundException()
        }
    }

    override fun getRandomNumberTrivia(): NumberTriviaModel {
        api.getRandomNumberTrivia().execute().run {
            body()?.let {
                return it.toModel()
            }
            throw NotFoundException()
        }
    }

    private fun NumberTriviaJson.toModel() = NumberTriviaModel(number = number, trivia = text)
}