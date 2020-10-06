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

        viewModel = getViewModel()
        viewModel.state.observe(this, { renderState(it) })

        binding.searchNumber
            .setOnClickListener { viewModel.searchNumberTrivia(binding.number.text.toString().toLong()) }
        binding.searchRandom
            .setOnClickListener { viewModel.searchRandomTrivia() }
    }

    private fun getViewModel(): NumbersTriviaViewModel {
        // TODO replace for Hilt injection
        val repository = NumberTriviaRepository(RetrofitRemoteSource(RetrofitApiManager.getInstance(applicationContext)), FakeLocalSource())
        val model = NumbersTriviaModel(
            GetNumberTrivia(repository),
            GetRandomNumberTrivia(repository)
        )
        return ViewModelProvider(this, NumbersTriviaViewModelFactory(model)).get(NumbersTriviaViewModel::class.java)
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
            NumbersTriviaViewModel.ViewState.NoConnection -> renderNoConnectivityState()
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
        binding.trivia.visibility = View.VISIBLE
        binding.triviaLoader.visibility = View.GONE
    }

    private fun renderNoConnectivityState() {
        binding.trivia.text = getString(R.string.no_connection)
        binding.triviaLoader.visibility = View.GONE
    }
}