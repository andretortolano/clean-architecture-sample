package br.com.andretortolano.data.sources.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class NumberTriviaTable(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val number: Long,
    val trivia: String
)