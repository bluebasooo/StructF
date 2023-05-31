package ru.mirea.structf.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mirea.structf.data.dao.DocDao
import ru.mirea.structf.data.dao.TagDao
import ru.mirea.structf.data.model.Doc
import ru.mirea.structf.data.model.Tag

@Database(
    entities = [Doc::class, Tag::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDocDao(): DocDao
    abstract fun getTagDao(): TagDao
}
