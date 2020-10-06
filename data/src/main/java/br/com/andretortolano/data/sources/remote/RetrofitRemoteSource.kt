package br.com.andretortolano.data.sources.remote

import br.com.andretortolano.data.exceptions.NotFoundException
import br.com.andretortolano.data.models.NumberTriviaModel
import br.com.andretortolano.data.sources.remote.retrofit.NumberTriviaJson
import br.com.andretortolano.data.sources.remote.retrofit.NumberTriviaRetrofit

class RetrofitRemoteSource(private val api: NumberTriviaRetrofit) : RemoteSource {

    override fun getConcreteNumberTrivia(number: Long): NumberTriviaModel {
        api.getConcreteNumberTrivia(number).execute().run {
            return body()?.let {
                if (it.found) {
                    it.toModel()
                } else {
                    throw NotFoundException()
                }
            } ?: throw NotFoundException()
        }
    }

    override fun getRandomNumberTrivia(): NumberTriviaModel {
        api.getRandomNumberTrivia().execute().run {
            return body()?.let {
                it.toModel()
            } ?: throw NotFoundException()
        }
    }

    private fun NumberTriviaJson.toModel() = NumberTriviaModel(number = number, trivia = text)
}