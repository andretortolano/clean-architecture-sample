package br.com.andretortolano.domain.entity

import java.math.BigInteger

data class NumberTriviaEntity(
    val number: BigInteger,
    val trivia: String,
    val found: Boolean
)