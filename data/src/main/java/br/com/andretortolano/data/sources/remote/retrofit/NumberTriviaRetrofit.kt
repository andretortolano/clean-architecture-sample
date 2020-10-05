package br.com.andretortolano.data.sources.remote.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NumberTriviaRetrofit {

    @GET("{number}?json")
    fun getConcreteNumberTrivia(@Path("number") number: Int): Call<NumberTriviaJson>

    @GET("random?json")
    fun getRandomNumberTrivia() : Call<NumberTriviaJson>
}