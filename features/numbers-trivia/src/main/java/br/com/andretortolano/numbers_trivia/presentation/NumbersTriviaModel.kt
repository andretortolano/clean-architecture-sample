package br.com.andretortolano.numbers_trivia.presentation

import br.com.andretortolano.domain.usecases.GetConcreteNumberTrivia
import br.com.andretortolano.domain.usecases.GetRandomNumberTrivia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class NumbersTriviaModel(
    private val getConcreteNumberTrivia: GetConcreteNumberTrivia,
    private val getRandomNumberTrivia: GetRandomNumberTrivia
) {

    suspend fun getConcreteNumberTriviaUseCase(number: Int): GetConcreteNumberTrivia.GetConcreteNumberTriviaResponse =
        withContext(Dispatchers.IO) {
            getConcreteNumberTrivia(number)
        }

    suspend fun getRandomNumberTriviaUseCase(): GetRandomNumberTrivia.GetRandomNumberTriviaResponse =
        withContext(Dispatchers.IO) {
            delay(5000)
            getRandomNumberTrivia()
        }
}