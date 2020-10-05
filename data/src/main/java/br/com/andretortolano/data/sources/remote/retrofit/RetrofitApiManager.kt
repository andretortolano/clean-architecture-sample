package br.com.andretortolano.data.sources.remote.retrofit

import android.content.Context

object RetrofitApiManager {

    private var instance: NumberTriviaRetrofit? = null

    fun getInstance(context: Context): NumberTriviaRetrofit {
        if(instance == null) {
            instance = RetrofitBuilder.build(context).create(NumberTriviaRetrofit::class.java)
        }

        return instance!!
    }
}