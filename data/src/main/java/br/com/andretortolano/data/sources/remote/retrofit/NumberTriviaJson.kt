package br.com.andretortolano.data.sources.remote.retrofit

import java.math.BigInteger

data class NumberTriviaJson(
    val text: String,
    val number: BigInteger,
    val found: Boolean,
    val type: String
)