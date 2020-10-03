package br.com.andretortolano.numbers_trivia

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import br.com.andretortolano.numbers_trivia.presentation.NumbersTriviaModel
import br.com.andretortolano.numbers_trivia.presentation.NumbersTriviaViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NumbersTriviaViewModelTest {

    @MockK
    lateinit var model: NumbersTriviaModel

    @InjectMockKs
    lateinit var viewModel: NumbersTriviaViewModel

    @RelaxedMockK
    lateinit var observer: Observer<NumbersTriviaViewModel.ViewState>

    private val slot = slot<NumbersTriviaViewModel.ViewState>()

    private val stateList: ArrayList<NumbersTriviaViewModel.ViewState> = arrayListOf()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        // must happen in this order so we can capture initial state
        every { observer.onChanged(capture(slot)) } answers { stateList.add(slot.captured) }
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `SHOUDD extend view model`() {
        assertThat(viewModel, Matchers.instanceOf(ViewModel::class.java))
    }

    @Test
    fun `SHOULD start with idle state`() {
        assertThat(stateList, Matchers.hasSize(1))
        assertThat(stateList[0], Matchers.instanceOf(NumbersTriviaViewModel.ViewState.Idle::class.java))
    }
}