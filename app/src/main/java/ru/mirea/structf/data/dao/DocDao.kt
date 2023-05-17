package ru.mirea.structf.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import ru.mirea.structf.data.model.Doc

@Dao
interface DocDao {

    @Upsert
    suspend fun insertDoc(doc: Doc)

    @Delete
    suspend fun deleteDoc(doc: Doc)

    @Query("SELECT * FROM doc_table ORDER BY name ASC")
    fun getDocsOrderedByName(): LiveData<List<Doc>>

    @Query("SELECT * FROM doc_table WHERE name = :name")
    suspend fun getDocByName(name: String): Doc?
}