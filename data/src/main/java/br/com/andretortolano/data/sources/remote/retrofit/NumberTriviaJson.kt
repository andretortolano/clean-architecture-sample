package br.com.andretortolano.data.sources.remote.retrofit

data class NumberTriviaJson(
    val text: String,
    val number: Long,
    val found: Boolean,
    val type: String
)