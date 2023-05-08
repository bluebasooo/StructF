package ru.mirea.structf.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mirea.structf.data.model.Doc

@Database(
    entities = [Doc::class],
    version = 1
)
abstract class DocDatabase: RoomDatabase() {

    abstract val dao: DocDao
}