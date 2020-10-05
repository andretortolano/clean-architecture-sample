package br.com.andretortolano.data.repositories

import br.com.andretortolano.data.models.NumberTriviaModel
import br.com.andretortolano.data.sources.local.LocalSource
import br.com.andretortolano.data.sources.remote.RemoteSource
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
        val numberTrivia = NumberTriviaModel(number.toBigInteger(), "trivia", true)
        every { localSource.getNumberTrivia(number) } returns null
        every { remoteSource.getConcreteNumberTrivia(number) } returns numberTrivia
        every { localSource.saveNumberTrivia(numberTrivia) } just Runs
        // When
        val result = repository.getNumberTrivia(number)
        // Then
        result.also {
            assertThat(it.number).isEqualTo(numberTrivia.number)
            assertThat(it.trivia).isEqualTo(numberTrivia.trivia)
            assertThat(it.found).isEqualTo(numberTrivia.found)
        }
    }

    @Test
    fun `SHOULD get specific NumberTrivia from local WHEN requesting a saved number`() {
        // Given
        val number = 1
        val numberTrivia = NumberTriviaModel(number.toBigInteger(), "trivia", true)
        every { localSource.getNumberTrivia(number) } returns numberTrivia
        // When
        val result = repository.getNumberTrivia(number)
        // Then
        result.also {
            assertThat(it.number).isEqualTo(numberTrivia.number)
            assertThat(it.trivia).isEqualTo(numberTrivia.trivia)
            assertThat(it.found).isEqualTo(numberTrivia.found)
        }
    }

    @Test
    fun `SHOULD save to local WHEN getting from remote`() {
        // Given
        val number = 1
        val expectedNumberTrivia = NumberTriviaModel(number.toBigInteger(), "trivia", true)
        every { localSource.getNumberTrivia(number) } returns null
        every { remoteSource.getConcreteNumberTrivia(number) } returns expectedNumberTrivia
        every { localSource.saveNumberTrivia(expectedNumberTrivia) } just Runs
        // When
        repository.getNumberTrivia(number)
        // Then
        verify(exactly = 1) {
            localSource.saveNumberTrivia(expectedNumberTrivia)
        }
    }

    @Test
    fun `SHOULD get random NumberTrivia from remote`() {
        // Given
        val randomNumberTrivia = NumberTriviaModel(1337.toBigInteger(), "trivia", true)
        every { remoteSource.getRandomNumberTrivia() } returns randomNumberTrivia
        every { localSource.saveNumberTrivia(randomNumberTrivia) } just Runs
        // When
        val result = repository.getRandomNumberTrivia()
        // Then
        result.also {
            assertThat(it.number).isEqualTo(randomNumberTrivia.number)
            assertThat(it.trivia).isEqualTo(randomNumberTrivia.trivia)
            assertThat(it.found).isEqualTo(randomNumberTrivia.found)
        }
    }

    @Test
    fun `SHOULD save random NumberTrivia WHEN getting from remote`() {
        // Given
        val number = 1337
        val randomNumberTrivia = NumberTriviaModel(number.toBigInteger(), "trivia", true)
        every { remoteSource.getRandomNumberTrivia() } returns randomNumberTrivia
        every { localSource.saveNumberTrivia(randomNumberTrivia) } just Runs
        // When
        repository.getRandomNumberTrivia()
        // Then
        verify(exactly = 1) { localSource.saveNumberTrivia(randomNumberTrivia) }
    }
}

