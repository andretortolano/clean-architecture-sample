package br.com.andretortolano.numbers_trivia.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NumbersTriviaViewModelFactory(private val model: NumbersTriviaModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(NumbersTriviaModel::class.java).newInstance(model)
    }
}