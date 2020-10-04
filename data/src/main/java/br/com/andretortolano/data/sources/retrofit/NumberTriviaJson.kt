package br.com.andretortolano.data.sources.retrofit

data class NumberTriviaJson(
    val text: String,
    val number: Int,
    val found: Boolean,
    val type: String
)