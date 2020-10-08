package br.com.andretortolano.data

import android.content.Context
import androidx.room.Room
import br.com.andretortolano.data.repositories.NumberTriviaRepository
import br.com.andretortolano.data.sources.local.LocalSource
import br.com.andretortolano.data.sources.local.RoomLocalSource
import br.com.andretortolano.data.sources.local.room.ModuleDataBase
import br.com.andretortolano.data.sources.remote.RemoteSource
import br.com.andretortolano.data.sources.remote.RetrofitRemoteSource
import br.com.andretortolano.data.sources.remote.retrofit.NetworkCheckerInterceptor
import br.com.andretortolano.data.sources.remote.retrofit.NumberTriviaRetrofit
import br.com.andretortolano.domain.gateway.NumberTriviaGateway
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataModule private constructor(
    val numberTriviaGateway: NumberTriviaGateway
) {

    class Builder(private val context: Context) {
        private var numberTriviaApiUrl: String? = null
        private var customRemoteSource: RemoteSource? = null
        private var customLocalSource: LocalSource? = null

        fun setNumberTriviaApi(url: String) = apply {
            numberTriviaApiUrl = url
        }

        fun setRemoteSource(remoteSource: RemoteSource) = apply {
            customRemoteSource = remoteSource
        }

        fun setLocalSource(localSource: LocalSource) = apply {
            customLocalSource = localSource
        }


        fun build(isDebug: Boolean = false): DataModule {
            val remoteSource = getRemoteSource(isDebug)
            val localSource = getLocalSource(isDebug)

            return DataModule(NumberTriviaRepository(remoteSource, localSource))
        }

        private fun getRemoteSource(isDebug: Boolean): RemoteSource {
            return if (customRemoteSource != null) {
                customRemoteSource!!
            } else {
                val numberTriviaRetrofit = numberTriviaApiUrl?.let {
                    getNumberTriviaRetrofit(getRetrofitBuilder(isDebug), it)
                } ?: throw ApiUrlNotDefinedException("Number Trivia")

                RetrofitRemoteSource(numberTriviaRetrofit)
            }
        }

        private fun getLocalSource(isDebug: Boolean): LocalSource {
            return if (customLocalSource != null) {
                customLocalSource!!
            } else {
                RoomLocalSource(getRoomBuilder(isDebug))
            }
        }

        private fun getRetrofitBuilder(isDebug: Boolean): Retrofit.Builder {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val clientBuilder = OkHttpClient.Builder().addInterceptor(NetworkCheckerInterceptor(context))

            if (isDebug) {
                clientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                })
            }

            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(clientBuilder.build())
        }

        private fun getNumberTriviaRetrofit(retrofitBuilder: Retrofit.Builder, url: String) = retrofitBuilder
            .baseUrl(url)
            .build()
            .create(NumberTriviaRetrofit::class.java)

        private fun getRoomBuilder(isDebug: Boolean): ModuleDataBase {
            return if(isDebug) {
                Room.inMemoryDatabaseBuilder(context, ModuleDataBase::class.java)
                    .build()
            } else {
                Room.databaseBuilder(context, ModuleDataBase::class.java, ModuleDataBase.DATABASE_NAME)
                    .build()
            }
        }
    }

    class ApiUrlNotDefinedException(api: String) : RuntimeException("Api url not defined for $api")
}