package br.com.andretortolano.data.sources.remote.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import br.com.andretortolano.data.exceptions.RemoteException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkCheckerInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (isNotConnected()) {
            throw RemoteException.NoConnectivityException
        }

        return chain.proceed(request)
    }

    private fun isNotConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        connectivityManager?.let {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                return !hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ||
                        !hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            }
        }
        return true
    }
}