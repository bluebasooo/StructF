package ru.mirea.structf.data

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.mirea.structf.data.model.Doc


interface DocRepository {

    suspend fun insertDoc(doc: Doc)

    suspend fun deleteDoc(doc: Doc)

    fun getDocsOrderedByName(): Flow<List<Doc>>

    suspend fun getDocById(id: Int): Doc?

}