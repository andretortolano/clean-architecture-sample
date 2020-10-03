package br.com.andretortolano.domain.usecases

import br.com.andretortolano.domain.entities.NumberTrivia
import br.com.andretortolano.domain.gateway.GatewayResponse
import br.com.andretortolano.domain.gateway.NumberTriviaGateway
import br.com.andretortolano.domain.usecases.GetRandomNumberTrivia.GetRandomNumberTriviaResponse
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Test

class GetRandomNumberTriviaTest : MockKTest() {

    @MockK
    private lateinit var numberTriviaGateway: NumberTriviaGateway

    @InjectMockKs
    private lateinit var getRandomNumberTrivia: GetRandomNumberTrivia

    @Test
    fun `SHOULD get a random number trivia`() {
        // Given
        val number = 42
        val trivia = "answer to everything"
        val expectedReturn = GatewayResponse(NumberTrivia(number, trivia))
        every { numberTriviaGateway.getRandomNumberTrivia() } returns expectedReturn
        // When
        val response = getRandomNumberTrivia()
        // Then
        (response as GetRandomNumberTriviaResponse.Success).run {
            assertThat(number).isEqualTo(numberTrivia.number)
            assertThat(trivia).isEqualTo(numberTrivia.triviaText)
        }
    }
}