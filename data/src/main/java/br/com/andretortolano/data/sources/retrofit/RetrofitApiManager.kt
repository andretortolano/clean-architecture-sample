package br.com.andretortolano.data.sources.retrofit

object RetrofitApiManager {
    val numberTriviaInstance: NumberTriviaRetrofit = RetrofitBuilder.build().create(NumberTriviaRetrofit::class.java)
}