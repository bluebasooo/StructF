package ru.mirea.structf.data

import kotlinx.coroutines.flow.Flow
import ru.mirea.structf.data.model.Doc

class DocRepositoryImpl(
    private val dao: DocDao
) : DocRepository {

    override suspend fun insertDoc(doc: Doc) {
        dao.insertDoc(doc)
    }

    override suspend fun deleteDoc(doc: Doc) {
        dao.deleteDoc(doc)
    }

    override fun getDocsOrderedByName(): Flow<List<Doc>> {
        return dao.getDocsOrderedByName()
    }

    override suspend fun getDocById(id: Int): Doc? {
        return dao.getDocById(id)
    }

}