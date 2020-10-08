package br.com.andretortolano.numbers_trivia.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.andretortolano.domain.entity.EntityResult
import br.com.andretortolano.domain.entity.ErrorEntity
import br.com.andretortolano.domain.entity.NumberTriviaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NumbersTriviaViewModel constructor(
    private val model: NumbersTriviaModel
) : ViewModel() {

    sealed class ViewState {
        object Idle : ViewState()
        object Loading : ViewState()
        data class NumberTriviaFound(val numberTrivia: NumberTriviaEntity) : ViewState()
        object NoConnection : ViewState()
        object NumberTriviaNotFound : ViewState()
    }

    private val _state = MutableLiveData<ViewState>().apply { value = ViewState.Idle }

    val state: LiveData<ViewState>
        get() = _state

    fun searchNumberTrivia(number: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = ViewState.Loading

            _state.value = model.getConcreteNumberTriviaUseCase(number).toViewState()
        }
    }

    fun searchRandomTrivia() {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = ViewState.Loading

            _state.value = model.getRandomNumberTriviaUseCase().toViewState()
        }
    }

    private fun EntityResult<NumberTriviaEntity>.toViewState(): ViewState {
        return when (this) {
            is EntityResult.Success -> ViewState.NumberTriviaFound(data)
            is EntityResult.Error -> when (error) {
                ErrorEntity.NoConnectivity -> ViewState.NoConnection
                ErrorEntity.NotFound -> ViewState.NumberTriviaNotFound
            }
        }
    }
}