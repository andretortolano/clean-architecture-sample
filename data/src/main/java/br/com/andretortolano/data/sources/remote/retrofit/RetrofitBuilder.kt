package br.com.andretortolano.data.sources.remote.retrofit

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitBuilder {

    companion object {
        fun build(context: Context): Retrofit {

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

            val networkCheckerInterceptor = NetworkCheckerInterceptor(context)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(networkCheckerInterceptor)

            return Retrofit.Builder()
                .baseUrl("http://numbersapi.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client.build())
                .build()
        }
    }
}