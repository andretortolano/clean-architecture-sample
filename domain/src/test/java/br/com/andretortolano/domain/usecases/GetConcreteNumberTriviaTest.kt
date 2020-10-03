package br.com.andretortolano.domain.usecases

import br.com.andretortolano.domain.entities.NumberTrivia
import br.com.andretortolano.domain.gateway.GatewayResponse
import br.com.andretortolano.domain.gateway.NumberTriviaGateway
import br.com.andretortolano.domain.usecases.GetConcreteNumberTrivia.GetConcreteNumberTriviaResponse
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Test

class GetConcreteNumberTriviaTest : MockKTest() {

    @MockK
    private lateinit var numberTriviaGateway: NumberTriviaGateway

    @InjectMockKs
    private lateinit var getConcreteNumberTrivia: GetConcreteNumberTrivia

    @Test
    fun `should get trivia for specific number`() {
        // given
        val number = 1
        val expectedTrivia = "expectedTrivia"
        val expectedResponse = GatewayResponse(NumberTrivia(number, expectedTrivia))
        every { numberTriviaGateway.getConcreteNumberTrivia(number) } returns expectedResponse
        // when
        val result = getConcreteNumberTrivia(number)
        // then
        (result as GetConcreteNumberTriviaResponse.Success).let {
            Assert.assertEquals(number, result.numberTrivia.number)
            Assert.assertEquals(expectedTrivia, result.numberTrivia.triviaText)
        }
    }
}

