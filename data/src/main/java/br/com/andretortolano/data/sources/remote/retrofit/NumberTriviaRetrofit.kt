package br.com.andretortolano.data.sources.remote.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

internal interface NumberTriviaRetrofit {

    @GET("{number}?json")
    fun getConcreteNumberTrivia(@Path("number") number: Long): Call<NumberTriviaJson>

    @GET("random?json&min=${Long.MIN_VALUE}&max=${Long.MAX_VALUE}")
    fun getRandomNumberTrivia() : Call<NumberTriviaJson>
}