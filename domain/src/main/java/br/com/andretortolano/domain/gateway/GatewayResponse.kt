package br.com.andretortolano.domain.gateway

data class GatewayResponse<T>(
    val value: T? = null,
    val error: Throwable? = null
)