package br.com.andretortolano.domain.entity

sealed class ErrorEntity {
    object NoConnectivity : ErrorEntity()
    object NotFound : ErrorEntity()
}