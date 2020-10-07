package br.com.andretortolano.data.sources.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
internal interface NumberTriviaDao {

    @Insert
    fun save(numberTriviaTable: NumberTriviaTable)

    @Query("SELECT * FROM NumberTriviaTable WHERE number = :number")
    fun getByNumber(number: Long): List<NumberTriviaTable>
}