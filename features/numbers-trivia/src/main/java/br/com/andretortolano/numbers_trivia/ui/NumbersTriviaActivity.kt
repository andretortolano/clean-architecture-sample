package br.com.andretortolano.numbers_trivia.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.andretortolano.numbers_trivia.R
import br.com.andretortolano.numbers_trivia.databinding.NumbersTriviaBinding
import br.com.andretortolano.numbers_trivia.di.DaggerFeatureComponent
import br.com.andretortolano.numbers_trivia.presentation.NumbersTriviaViewModel
import br.com.andretortolano.numbers_trivia.presentation.NumbersTriviaViewModelFactory
import br.com.andretortolano.shared.di.SharedComponent
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class NumbersTriviaActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, NumbersTriviaActivity::class.java)
        }
    }

    @Inject
    lateinit var viewModelFactory: NumbersTriviaViewModelFactory

    private val viewModel by lazy { viewModelFactory.getViewModel(this) }

    private var _binding: NumbersTriviaBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFeatureComponent.builder()
            .context(this)
            .featureDependencies(EntryPointAccessors.fromApplication(applicationContext, SharedComponent::class.java))
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
        _binding = NumbersTriviaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.state.observe(this, { renderState(it) })

        binding.searchNumber
            .setOnClickListener { viewModel.searchNumberTrivia(binding.number.text.toString().toLong()) }
        binding.searchRandom
            .setOnClickListener { viewModel.searchRandomTrivia() }
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