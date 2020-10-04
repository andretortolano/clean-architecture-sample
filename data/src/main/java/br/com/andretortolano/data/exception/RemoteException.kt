package br.com.andretortolano.data.exception

import java.lang.Exception

sealed class RemoteException : Exception() {
    object NoInternet: RemoteException()
    object NoResult: RemoteException()
    object RemoteError: RemoteException()
}