package br.com.andretortolano.domain

import br.com.andretortolano.domain.entity.NumberTriviaEntity
import br.com.andretortolano.domain.gateway.NumberTriviaGateway
import br.com.andretortolano.domain.usecase.GetNumberTrivia
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class GetNumberTriviaTest {

    @MockK
    private lateinit var getNumberTriviaGateway: NumberTriviaGateway

    @InjectMockKs
    private lateinit var useCase: GetNumberTrivia

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `SHOULD get NumberTrivia for a number from gateway`() {
        // Given
        val number = 1
        val numberTrivia = NumberTriviaEntity(number.toBigInteger(), "trivia test", true)
        every { getNumberTriviaGateway.getNumberTrivia(number) } returns numberTrivia
        // When
        val response = useCase(number)
        // Then
        assertThat(response).isEqualTo(numberTrivia)
    }
}

