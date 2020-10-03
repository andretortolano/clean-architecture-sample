package br.com.andretortolano.numbers_trivia

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import br.com.andretortolano.domain.entities.NumberTrivia
import br.com.andretortolano.domain.usecases.GetConcreteNumberTrivia
import br.com.andretortolano.domain.usecases.GetRandomNumberTrivia
import br.com.andretortolano.numbers_trivia.presentation.NumbersTriviaModel
import br.com.andretortolano.numbers_trivia.presentation.NumbersTriviaViewModel
import br.com.andretortolano.numbers_trivia.presentation.NumbersTriviaViewModel.ViewState
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NumbersTriviaViewModelTest {

    @MockK
    lateinit var model: NumbersTriviaModel

    @InjectMockKs
    lateinit var viewModel: NumbersTriviaViewModel

    @RelaxedMockK
    lateinit var observer: Observer<ViewState>

    private val slot = slot<ViewState>()

    private val stateList: ArrayList<ViewState> = arrayListOf()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        // must happen in this order so we can capture initial state
        every { observer.onChanged(capture(slot)) } answers { stateList.add(slot.captured) }
        viewModel.state.observeForever(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `SHOULD extend view model`() {
        assertThat(viewModel).isInstanceOf(ViewModel::class.java)
    }

    @Test
    fun `SHOULD start with Idle state`() {
        assertThat(stateList).hasSize(1)
        assertThat(stateList[0]).isInstanceOf(ViewState.Idle::class.java)
    }

    @Test
    fun `SHOULD emmit Loading state WHEN searching any number trivia`() = runBlockingTest {
        // Given
        coEvery { model.getConcreteNumberTriviaUseCase(any()) } returns
                mockk<GetConcreteNumberTrivia.GetConcreteNumberTriviaResponse.Success>(relaxed = true)
        // When
        viewModel.searchNumberTrivia(1)
        // Then
        assertThat(stateList[1]).isInstanceOf(ViewState.Loading::class.java)
    }

    @Test
    fun `SHOULD emmit NumberTriviaFound state WITH correct NumberTrivia WHEN searching any number trivia`() = runBlockingTest {
        // Given
        val number = 20
        val trivia = "trivia for number 20"
        val expectedNumberTrivia = NumberTrivia(number, trivia)
        coEvery { model.getConcreteNumberTriviaUseCase(any()) } returns
                GetConcreteNumberTrivia.GetConcreteNumberTriviaResponse.Success(expectedNumberTrivia)
        // When
        viewModel.searchNumberTrivia(number)
        // Then
        (stateList[2] as ViewState.NumberTriviaFound).run {
            assertThat(numberTrivia.number).isEqualTo(number)
            assertThat(numberTrivia.triviaText).isEqualTo(trivia)
        }
    }

    @Test
    fun `SHOULD emmit Loading state WHEN searching for random number trivia`() = runBlockingTest {
        // Given
        coEvery { model.getRandomNumberTriviaUseCase() } returns
                mockk<GetRandomNumberTrivia.GetRandomNumberTriviaResponse.Success>(relaxed = true)
        // When
        viewModel.searchRandomTrivia()
        // Then
        assertThat(stateList[1]).isInstanceOf(ViewState.Loading::class.java)
    }

    @Test
    fun `SHOULD emmit NumberTriviaFound state WITH correct NumberTrivia WHEN searching for random number trivia`() = runBlockingTest {
        // Given
        val number = 20
        val trivia = "trivia for number 20"
        val expectedNumberTrivia = NumberTrivia(number, trivia)
        coEvery { model.getRandomNumberTriviaUseCase() } returns
                GetRandomNumberTrivia.GetRandomNumberTriviaResponse.Success(expectedNumberTrivia)
        // When
        viewModel.searchRandomTrivia()
        // Then
        (stateList[2] as ViewState.NumberTriviaFound).run {
            assertThat(numberTrivia.number).isEqualTo(number)
            assertThat(numberTrivia.triviaText).isEqualTo(trivia)
        }
    }
}