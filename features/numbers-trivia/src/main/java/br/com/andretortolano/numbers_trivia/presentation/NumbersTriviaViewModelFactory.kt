package br.com.andretortolano.numbers_trivia.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import javax.inject.Inject

class NumbersTriviaViewModelFactory @Inject constructor(private val model: NumbersTriviaModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(NumbersTriviaModel::class.java).newInstance(model)
    }

    fun getViewModel(owner: ViewModelStoreOwner) = ViewModelProvider(owner, this).get(NumbersTriviaViewModel::class.java)
}