package br.com.andretortolano.domain.entity

sealed class EntityResult<T> {
    data class Success<T>(val data: T): EntityResult<T>()
    data class Error<T>(val error: ErrorEntity): EntityResult<T>()
}