package br.com.andretortolano.data.sources.local

import br.com.andretortolano.data.models.NumberTriviaModel
import br.com.andretortolano.data.sources.local.room.ModuleDataBase
import br.com.andretortolano.data.sources.local.room.NumberTriviaTable
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

internal class RoomLocalSourceTest {
    @MockK
    lateinit var moduleDataBase: ModuleDataBase

    @InjectMockKs
    lateinit var source: RoomLocalSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `class SHOULD implement LocalSource`() {
        assertThat(RoomLocalSource::class.java).isAssignableTo(LocalSource::class.java)
    }

    @Test
    fun `getNumberTrivia SHOULD return null if list is empty`() {
        // Given
        every { moduleDataBase.numberTriviaDao().getByNumber(any()) } returns listOf()
        // When
        val result = source.getNumberTrivia(1)
        // Then
        assertThat(result).isNull()
    }

    @Test
    fun `getNumberTrivia SHOULD should return a random item from the list`() {
        // Given
        val list = listOf(
            NumberTriviaTable(id = 1, number = 1, trivia = "A"),
            NumberTriviaTable(id = 2, number = 1, trivia = "B"),
            NumberTriviaTable(id = 3, number = 1, trivia = "C")
        )
        every { moduleDataBase.numberTriviaDao().getByNumber(any()) } returns list
        // When
        val result = source.getNumberTrivia(1)
        // Then
        assertThat(result).isNotNull()
        assertThat(list.any { it.trivia == result?.trivia })
    }

    @Test
    fun `saveNumberTrivia SHOULD save if there is no other element with same trivia`() {
        // Given
        val list = listOf(
            NumberTriviaTable(id = 1, number = 1, trivia = "A"),
            NumberTriviaTable(id = 2, number = 1, trivia = "B"),
            NumberTriviaTable(id = 3, number = 1, trivia = "C")
        )
        val savingElement = NumberTriviaModel(number = 1, trivia = "D")
        every { moduleDataBase.numberTriviaDao().getByNumber(1) } returns list
        every { moduleDataBase.numberTriviaDao().save(any()) } just Runs
        // When
        source.saveNumberTrivia(savingElement)
        // Then
        val slot = slot<NumberTriviaTable>()
        verify {
            moduleDataBase.numberTriviaDao().save(capture(slot))
        }

        assertThat(slot.captured.number).isEqualTo(savingElement.number)
        assertThat(slot.captured.trivia).isEqualTo(savingElement.trivia)
    }

    @Test
    fun `saveNumberTrivia SHOULD not save if there is an element with same trivia`() {
        // Given
        val list = listOf(
            NumberTriviaTable(id = 1, number = 1, trivia = "A"),
            NumberTriviaTable(id = 2, number = 1, trivia = "B"),
            NumberTriviaTable(id = 3, number = 1, trivia = "C")
        )
        val savingElement = NumberTriviaModel(number = 1, trivia = "C")
        every { moduleDataBase.numberTriviaDao().getByNumber(1) } returns list
        every { moduleDataBase.numberTriviaDao().save(any()) } just Runs
        // When
        source.saveNumberTrivia(savingElement)
        // Then
        val slot = slot<NumberTriviaTable>()
        verify(inverse = true) {
            moduleDataBase.numberTriviaDao().save(any())
        }
    }
}