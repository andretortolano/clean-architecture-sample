package br.com.andretortolano.domain

import br.com.andretortolano.domain.entity.NumberTriviaEntity
import br.com.andretortolano.domain.gateway.NumberTriviaGateway
import br.com.andretortolano.domain.usecase.GetRandomNumberTrivia
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class GetRandomNumberTriviaTest {

    @MockK
    private lateinit var numberTriviaGateway: NumberTriviaGateway

    @InjectMockKs
    private lateinit var useCase: GetRandomNumberTrivia

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `SHOULD get random NumberTrivia from gateway`() {
        // Given
        val numberTrivia = NumberTriviaEntity(1.toBigInteger(), "trivia test", true)
        every { numberTriviaGateway.getRandomNumberTrivia() } returns numberTrivia
        // When
        val response = useCase()
        // Then
        assertThat(response).isEqualTo(numberTrivia)
    }
}

