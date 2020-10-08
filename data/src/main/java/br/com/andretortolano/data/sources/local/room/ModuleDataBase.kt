package br.com.andretortolano.data.sources.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [NumberTriviaTable::class])
internal abstract class ModuleDataBase: RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "module_room.db"
    }

    abstract fun numberTriviaDao(): NumberTriviaDao
}