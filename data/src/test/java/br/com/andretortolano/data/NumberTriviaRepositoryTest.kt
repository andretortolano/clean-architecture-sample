package br.com.andretortolano.data

import br.com.andretortolano.data.models.NumberTriviaModel
import br.com.andretortolano.data.repositories.NumberTriviaRepository
import br.com.andretortolano.data.sources.LocalSource
import br.com.andretortolano.data.sources.RemoteSource
import br.com.andretortolano.domain.entities.NumberTrivia
import br.com.andretortolano.domain.gateway.NumberTriviaGateway
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class NumberTriviaRepositoryTest {

    @MockK
    lateinit var remoteSource: RemoteSource

    @MockK
    lateinit var localSource: LocalSource

    @InjectMockKs
    lateinit var repository: NumberTriviaRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `SHOULD implement NumberTriviaGateway from domain`() {
        assertThat(repository).isInstanceOf(NumberTriviaGateway::class.java)
    }

    @Test
    fun `SHOULD get specific NumberTrivia from remote WHEN requesting a new number`() {
        // Given
        val number = 1
        val trivia = "number 1 trivia"
        val expectedNumberTrivia = NumberTriviaModel(number, trivia)
        every { localSource.getNumberTrivia(number) } returns null
        every { remoteSource.getConcreteNumberTrivia(number) } returns expectedNumberTrivia
        every { localSource.saveNumberTrivia(expectedNumberTrivia) } just Runs
        // When
        val result = repository.getConcreteNumberTrivia(number)
        // Then
        assertThat(result.error).isNull()
        assertThat(result.value).isEqualTo(NumberTrivia(number, trivia))
    }

    @Test
    fun `SHOULD get specific NumberTrivia from local WHEN requesting a saved number`() {
        // Given
        val number = 1
        val trivia = "number 1 trivia"
        val expectedNumberTrivia = NumberTriviaModel(number, trivia)
        every { localSource.getNumberTrivia(number) } returns expectedNumberTrivia
        // When
        val result = repository.getConcreteNumberTrivia(number)
        // Then
        assertThat(result.error).isNull()
        assertThat(result.value).isEqualTo(NumberTrivia(number, trivia))
    }

    @Test
    fun `SHOULD save to local WHEN getting from remote`() {
        // Given
        val number = 1
        val trivia = "new number 1 - new trivia"
        val expectedNumberTrivia = NumberTriviaModel(number, trivia)
        every { localSource.getNumberTrivia(number) } returns null
        every { remoteSource.getConcreteNumberTrivia(number) } returns expectedNumberTrivia
        every { localSource.saveNumberTrivia(expectedNumberTrivia) } just Runs
        // When
        repository.getConcreteNumberTrivia(number)
        // Then
        verify(exactly = 1) {
            localSource.saveNumberTrivia(expectedNumberTrivia)
        }
    }

    @Test
    fun `SHOULD get random NumberTrivia from remote`() {
        // Given
        val number = 1337
        val trivia = "trivia random number 1337"
        val randomNumberTrivia = NumberTriviaModel(number, trivia)
        every { remoteSource.getRandomNumberTrivia() } returns randomNumberTrivia
        every { localSource.saveNumberTrivia(randomNumberTrivia) } just Runs
        // When
        val result = repository.getRandomNumberTrivia()
        // Then
        assertThat(result.error).isNull()
        assertThat(result.value).isEqualTo(NumberTrivia(number, trivia))
    }

    @Test
    fun `SHOULD save random NumberTrivia WHEN getting from remote`() {
        // Given
        val number = 1337
        val trivia = "trivia random number 1337"
        val randomNumberTrivia = NumberTriviaModel(number, trivia)
        every { remoteSource.getRandomNumberTrivia() } returns randomNumberTrivia
        every { localSource.saveNumberTrivia(randomNumberTrivia) } just Runs
        // When
        repository.getRandomNumberTrivia()
        // Then
        verify(exactly = 1) { localSource.saveNumberTrivia(randomNumberTrivia) }
    }
}

