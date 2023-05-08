package ru.mirea.structf.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.mirea.structf.data.model.Doc

@Dao
interface DocDao {

    @Upsert
    suspend fun insertDoc(doc: Doc)

    @Delete
    suspend fun deleteDoc(doc: Doc)

    @Query("select * from doc order by name asc")
    fun getDocsOrderedByName(): Flow<List<Doc>>

    @Query("select * from doc where id = :id")
    suspend fun getDocById(id: Int): Doc?
}