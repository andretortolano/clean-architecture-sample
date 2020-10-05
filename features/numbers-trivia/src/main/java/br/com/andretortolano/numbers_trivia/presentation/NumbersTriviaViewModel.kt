package br.com.andretortolano.numbers_trivia.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.andretortolano.data.exceptions.RemoteException
import br.com.andretortolano.domain.entity.NumberTriviaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NumbersTriviaViewModel(private val model: NumbersTriviaModel) : ViewModel(), Thread.UncaughtExceptionHandler {

    sealed class ViewState {
        object Idle : ViewState()
        object Loading : ViewState()
        data class NumberTriviaFound(val numberTrivia: NumberTriviaEntity) : ViewState()
        object SomethingWentWrong : ViewState()
        object NumberTriviaNotFound : ViewState()
    }

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun onCleared() {
        super.onCleared()
        Thread.setDefaultUncaughtExceptionHandler(null)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        when(e) {
            is RemoteException -> handleRemoteException(e)
            else -> throw e
        }
    }

    private val _state = MutableLiveData<ViewState>().apply { value = ViewState.Idle }

    val state: LiveData<ViewState>
        get() = _state

    fun searchNumberTrivia(number: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = ViewState.Loading

            _state.value = model.getConcreteNumberTriviaUseCase(number).run {
                if (found) {
                    ViewState.NumberTriviaFound(this)
                } else {
                    ViewState.NumberTriviaNotFound
                }
            }
        }
    }

    fun searchRandomTrivia() {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = ViewState.Loading

            _state.value = model.getRandomNumberTriviaUseCase().run {
                if (found) {
                    ViewState.NumberTriviaFound(this)
                } else {
                    ViewState.NumberTriviaNotFound
                }
            }
        }
    }

    fun handleRemoteException(exception: RemoteException) {
        _state.value = ViewState.SomethingWentWrong
    }
}