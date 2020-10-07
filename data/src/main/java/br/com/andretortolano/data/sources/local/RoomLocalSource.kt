package br.com.andretortolano.data.sources.local

import br.com.andretortolano.data.models.NumberTriviaModel
import br.com.andretortolano.data.sources.local.room.ModuleDataBase
import br.com.andretortolano.data.sources.local.room.NumberTriviaTable

internal class RoomLocalSource(private val db: ModuleDataBase) : LocalSource {
    override fun getNumberTrivia(number: Long): NumberTriviaModel? {
        return db.numberTriviaDao().getByNumber(number).randomOrNull()?.toModel()
    }

    override fun saveNumberTrivia(numberTrivia: NumberTriviaModel) {
        val savedList = db.numberTriviaDao().getByNumber(numberTrivia.number)
        if (savedList.none { it.trivia == numberTrivia.trivia }) {
            db.numberTriviaDao().save(numberTrivia.toTable())
        }
    }

    private fun NumberTriviaTable.toModel() = NumberTriviaModel(number, trivia)
    private fun NumberTriviaModel.toTable() = NumberTriviaTable(number = number, trivia = trivia)
}



