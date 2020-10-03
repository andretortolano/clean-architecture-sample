package br.com.andretortolano.numbers_trivia.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.andretortolano.domain.entities.NumberTrivia
import br.com.andretortolano.domain.usecases.GetConcreteNumberTrivia
import br.com.andretortolano.domain.usecases.GetRandomNumberTrivia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NumbersTriviaViewModel(private val model: NumbersTriviaModel) : ViewModel() {

    sealed class ViewState {
        object Idle : ViewState()
        object Loading : ViewState()
        data class NumberTriviaFound(val numberTrivia: NumberTrivia) : ViewState()
        object NumberTriviaNotFound : ViewState()
    }

    private val _state = MutableLiveData<ViewState>().apply { value = ViewState.Idle }

    val state: LiveData<ViewState>
        get() = _state

    fun searchNumberTrivia(number: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = ViewState.Loading

            _state.value = model.getConcreteNumberTriviaUseCase(number).run {
                when (this) {
                    is GetConcreteNumberTrivia.GetConcreteNumberTriviaResponse.Success ->
                        ViewState.NumberTriviaFound(numberTrivia)
                    GetConcreteNumberTrivia.GetConcreteNumberTriviaResponse.NoTriviaFoundError ->
                        ViewState.NumberTriviaNotFound
                }
            }
        }
    }

    fun searchRandomTrivia() {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = ViewState.Loading

            _state.value = model.getRandomNumberTriviaUseCase().run {
                when(this) {
                    is GetRandomNumberTrivia.GetRandomNumberTriviaResponse.Success ->
                        ViewState.NumberTriviaFound(numberTrivia)
                    GetRandomNumberTrivia.GetRandomNumberTriviaResponse.NoTriviaFoundError ->
                        ViewState.NumberTriviaNotFound
                }
            }
        }
    }
}