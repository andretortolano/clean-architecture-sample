package br.com.andretortolano.data.exceptions

import java.lang.Exception

sealed class RemoteException : Exception() {
    object NoConnectivityException: RemoteException()
    object NotFoundException: RemoteException()
    object UnknownRemoteException: RemoteException()
}