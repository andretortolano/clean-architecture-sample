package br.com.andretortolano.numbers_trivia

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import br.com.andretortolano.domain.entity.EntityResult
import br.com.andretortolano.domain.entity.ErrorEntity
import br.com.andretortolano.domain.entity.NumberTriviaEntity
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
    fun `class SHOULD extend view model`() {
        assertThat(viewModel).isInstanceOf(ViewModel::class.java)
    }

    @Test
    fun `state SHOULD start as Idle`() {
        assertThat(stateList).hasSize(1)
        assertThat(stateList[0]).isInstanceOf(ViewState.Idle::class.java)
    }

    @Test
    fun `searchNumberTrivia SHOULD emmit Loading state`() = runBlockingTest {
        // Given
        val entityResult = EntityResult.Success(NumberTriviaEntity(10, ""))
        coEvery { model.getConcreteNumberTriviaUseCase(any()) } returns entityResult
        // When
        viewModel.searchNumberTrivia(1)
        // Then
        assertThat(stateList[1]).isInstanceOf(ViewState.Loading::class.java)
    }

    @Test
    fun `searchNumberTrivia SHOULD emmit NumberTriviaFound state WITH correct NumberTrivia`() = runBlockingTest {
        // Given
        val number = 20L
        val trivia = "trivia"
        val entityResult = EntityResult.Success(NumberTriviaEntity(number, trivia))
        coEvery { model.getConcreteNumberTriviaUseCase(number) } returns entityResult
        // When
        viewModel.searchNumberTrivia(number)
        // Then
        (stateList[2] as ViewState.NumberTriviaFound).run {
            assertThat(numberTrivia.number).isEqualTo(number)
            assertThat(numberTrivia.trivia).isEqualTo(trivia)
        }
    }

    @Test
    fun `searchNumberTrivia SHOULD emmit NumberTriviaNotFound state WHEN model returns ErrorEntity_NotFound`() = runBlockingTest {
        // Given
        val number = 20L
        val entityResult = EntityResult.Error<NumberTriviaEntity>(ErrorEntity.NotFound)
        coEvery { model.getConcreteNumberTriviaUseCase(number) } returns entityResult
        // When
        viewModel.searchNumberTrivia(number)
        // Then
        assertThat(stateList[2]).isInstanceOf(ViewState.NumberTriviaNotFound::class.java)
    }

    @Test
    fun `searchNumberTrivia SHOULD emmit NoConnection state WHEN model returns ErrorEntity_NoConnectivity`() = runBlockingTest {
        // Given
        val number = 20L
        val entityResult = EntityResult.Error<NumberTriviaEntity>(ErrorEntity.NoConnectivity)
        coEvery { model.getConcreteNumberTriviaUseCase(number) } returns entityResult
        // When
        viewModel.searchNumberTrivia(number)
        // Then
        assertThat(stateList[2]).isInstanceOf(ViewState.NoConnection::class.java)
    }

    @Test
    fun `searchRandomTrivia SHOULD emmit Loading state`() = runBlockingTest {
        // Given
        val entityResult = EntityResult.Success(NumberTriviaEntity(10, ""))
        coEvery { model.getRandomNumberTriviaUseCase() } returns entityResult
        // When
        viewModel.searchRandomTrivia()
        // Then
        assertThat(stateList[1]).isInstanceOf(ViewState.Loading::class.java)
    }

    @Test
    fun `searchRandomTrivia SHOULD emmit NumberTriviaFound state WITH correct NumberTrivia`() =
        runBlockingTest {
            // Given
            val number = 20L
            val trivia = "trivia"
            val entityResult = EntityResult.Success(NumberTriviaEntity(number, trivia))
            coEvery { model.getRandomNumberTriviaUseCase() } returns entityResult
            // When
            viewModel.searchRandomTrivia()
            // Then
            (stateList[2] as ViewState.NumberTriviaFound).run {
                assertThat(numberTrivia.number).isEqualTo(number)
                assertThat(numberTrivia.trivia).isEqualTo(trivia)
            }
        }

    @Test
    fun `searchRandomTrivia SHOULD emmit NoConnection state WHEN model returns ErrorEntity_NoConnectivity`() = runBlockingTest {
        // Given
        val entityResult = EntityResult.Error<NumberTriviaEntity>(ErrorEntity.NoConnectivity)
        coEvery { model.getRandomNumberTriviaUseCase() } returns entityResult
        // When
        viewModel.searchRandomTrivia()
        // Then
        assertThat(stateList[2]).isInstanceOf(ViewState.NoConnection::class.java)
    }

    @Test
    fun `searchRandomTrivia SHOULD emmit NumberTriviaNotFound state WHEN model returns ErrorEntity_NotFound`() = runBlockingTest {
        // Given
        val entityResult = EntityResult.Error<NumberTriviaEntity>(ErrorEntity.NotFound)
        coEvery { model.getRandomNumberTriviaUseCase() } returns entityResult
        // When
        viewModel.searchRandomTrivia()
        // Then
        assertThat(stateList[2]).isInstanceOf(ViewState.NumberTriviaNotFound::class.java)
    }

}