package br.com.andretortolano.domain.usecases

import br.com.andretortolano.domain.entities.NumberTrivia
import br.com.andretortolano.domain.gateway.GatewayResponse
import br.com.andretortolano.domain.gateway.NumberTriviaGateway
import br.com.andretortolano.domain.usecases.GetConcreteNumberTrivia.GetConcreteNumberTriviaResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Test

class GetConcreteNumberTriviaTest : MockKTest() {

    @MockK
    private lateinit var numberTriviaGateway: NumberTriviaGateway

    @InjectMockKs
    private lateinit var getConcreteNumberTrivia: GetConcreteNumberTrivia

    @Test
    fun `SHOULD get trivia for specific number`() {
        // given
        val number = 1
        val expectedTrivia = "expectedTrivia"
        val expectedResponse = GatewayResponse(NumberTrivia(number, expectedTrivia))
        every { numberTriviaGateway.getConcreteNumberTrivia(number) } returns expectedResponse
        // when
        val result = getConcreteNumberTrivia(number)
        // then
        (result as GetConcreteNumberTriviaResponse.Success).run {
            assertThat(number).isEqualTo(numberTrivia.number)
            assertThat(expectedTrivia).isEqualTo(numberTrivia.triviaText)
        }
    }
}

