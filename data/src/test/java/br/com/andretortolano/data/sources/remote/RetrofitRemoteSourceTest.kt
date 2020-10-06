package br.com.andretortolano.data.sources.remote

import br.com.andretortolano.data.exceptions.NotFoundException
import br.com.andretortolano.data.sources.remote.retrofit.NumberTriviaJson
import br.com.andretortolano.data.sources.remote.retrofit.NumberTriviaRetrofit
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.HttpURLConnection

internal class RetrofitRemoteSourceTest {

    @MockK
    lateinit var numberTriviaRetrofit: NumberTriviaRetrofit

    @InjectMockKs
    lateinit var source: RetrofitRemoteSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `class SHOULD implement RemoteSource`() {
        assertThat(RetrofitRemoteSource::class.java).isAssignableTo(RemoteSource::class.java)
    }

    @Test
    fun `getConcreteNumberTrivia SHOULD return model WHEN response success`() {
        // Given
        val number = 10L
        val trivia = "trivia text"
        val numberTriviaJson = NumberTriviaJson(trivia, number, true, "")
        every { numberTriviaRetrofit.getConcreteNumberTrivia(any()).execute() } returns Response.success(numberTriviaJson)
        // When
        val result = source.getConcreteNumberTrivia(number)
        // Then
        assertThat(result.number).isEqualTo(number)
        assertThat(result.trivia).isEqualTo(trivia)
    }

    @Test(expected = NotFoundException::class)
    fun `getConcreteNumberTrivia SHOULD throw NotFoundException WHEN response error`() {
        // Given
        every {
            numberTriviaRetrofit.getConcreteNumberTrivia(any()).execute()
        } returns Response.error(HttpURLConnection.HTTP_INTERNAL_ERROR, mockk())
        // When
        source.getConcreteNumberTrivia(1)
    }

    @Test(expected = NotFoundException::class)
    fun `getConcreteNumberTrivia SHOULD throw NotFoundException WHEN response found is false`() {
        // Given
        val json = NumberTriviaJson("", 1L, false, "")
        every { numberTriviaRetrofit.getConcreteNumberTrivia(any()).execute() } returns Response.success(json)
        // When
        source.getConcreteNumberTrivia(1)
    }

    @Test
    fun `getRandomNumberTrivia SHOULD return model WHEN response success`() {
        // Given
        val number = 10L
        val trivia = "trivia text"
        val numberTriviaJson = NumberTriviaJson(trivia, number, true, "")
        every { numberTriviaRetrofit.getRandomNumberTrivia().execute() } returns Response.success(numberTriviaJson)
        // When
        val result = source.getRandomNumberTrivia()
        // Then
        assertThat(result.number).isEqualTo(number)
        assertThat(result.trivia).isEqualTo(trivia)
    }

    @Test(expected = NotFoundException::class)
    fun `getRandomNumberTrivia SHOULD throw NotFoundException WHEN response error`() {
        // Given
        every {
            numberTriviaRetrofit.getRandomNumberTrivia().execute()
        } returns Response.error(HttpURLConnection.HTTP_INTERNAL_ERROR, mockk())
        // When
        source.getRandomNumberTrivia()
    }
}