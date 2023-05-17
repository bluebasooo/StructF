package ru.mirea.structf.data.repository

import androidx.lifecycle.LiveData
import ru.mirea.structf.data.model.Doc


interface DocRepository {

    suspend fun insertDoc(doc: Doc)

    suspend fun deleteDoc(doc: Doc)

    fun getDocsOrderedByName(): LiveData<List<Doc>>

    suspend fun getDocByName(name: String): Doc?

}