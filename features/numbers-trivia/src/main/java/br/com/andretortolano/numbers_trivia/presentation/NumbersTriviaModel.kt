package br.com.andretortolano.numbers_trivia.presentation

import br.com.andretortolano.domain.entity.NumberTriviaEntity
import br.com.andretortolano.domain.usecase.GetNumberTrivia
import br.com.andretortolano.domain.usecase.GetRandomNumberTrivia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NumbersTriviaModel(
    private val getConcreteNumberTrivia: GetNumberTrivia,
    private val getRandomNumberTrivia: GetRandomNumberTrivia
) {

    suspend fun getConcreteNumberTriviaUseCase(number: Int): NumberTriviaEntity =
        withContext(Dispatchers.IO) {
            getConcreteNumberTrivia(number)
        }

    suspend fun getRandomNumberTriviaUseCase(): NumberTriviaEntity =
        withContext(Dispatchers.IO) {
            getRandomNumberTrivia()
        }
}