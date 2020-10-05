package br.com.andretortolano.numbers_trivia.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.andretortolano.data.repositories.NumberTriviaRepository
import br.com.andretortolano.data.sources.local.FakeLocalSource
import br.com.andretortolano.data.sources.remote.RetrofitRemoteSource
import br.com.andretortolano.data.sources.remote.retrofit.RetrofitApiManager
import br.com.andretortolano.domain.usecase.GetNumberTrivia
import br.com.andretortolano.domain.usecase.GetRandomNumberTrivia
import br.com.andretortolano.numbers_trivia.R
import br.com.andretortolano.numbers_trivia.databinding.NumbersTriviaBinding
import br.com.andretortolano.numbers_trivia.presentation.NumbersTriviaModel
import br.com.andretortolano.numbers_trivia.presentation.NumbersTriviaViewModel
import br.com.andretortolano.numbers_trivia.presentation.NumbersTriviaViewModelFactory

class NumbersTriviaActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, NumbersTriviaActivity::class.java)
        }
    }

    private lateinit var viewModel: NumbersTriviaViewModel

    private var _binding: NumbersTriviaBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = NumbersTriviaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = NumberTriviaRepository(RetrofitRemoteSource(RetrofitApiManager.numberTriviaInstance), FakeLocalSource())
        val model = NumbersTriviaModel(
            GetNumberTrivia(repository),
            GetRandomNumberTrivia(repository)
        )

        viewModel = ViewModelProvider(this, NumbersTriviaViewModelFactory(model)).get(NumbersTriviaViewModel::class.java)

        viewModel.state.observe(this, { renderState(it) })

        binding.searchNumber.setOnClickListener {
            val number = Integer.valueOf(binding.number.text.toString())
            viewModel.searchNumberTrivia(number)
        }
        binding.searchRandom.setOnClickListener { viewModel.searchRandomTrivia() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun renderState(state: NumbersTriviaViewModel.ViewState) {
        when (state) {
            NumbersTriviaViewModel.ViewState.Idle -> renderIdleState()
            NumbersTriviaViewModel.ViewState.Loading -> renderLoadingState()
            is NumbersTriviaViewModel.ViewState.NumberTriviaFound -> renderFoundState(state)
            NumbersTriviaViewModel.ViewState.NumberTriviaNotFound -> renderNotFoundState()
        }
    }

    private fun renderIdleState() {
        binding.trivia.visibility = View.GONE
        binding.triviaLoader.visibility = View.GONE
    }

    private fun renderLoadingState() {
        binding.trivia.visibility = View.GONE
        binding.triviaLoader.visibility = View.VISIBLE
    }

    private fun renderFoundState(state: NumbersTriviaViewModel.ViewState.NumberTriviaFound) {
        binding.trivia.text = state.numberTrivia.trivia
        binding.trivia.visibility = View.VISIBLE
        binding.triviaLoader.visibility = View.GONE
    }

    private fun renderNotFoundState() {
        binding.trivia.text = getString(R.string.no_trivia_found)
    }
}