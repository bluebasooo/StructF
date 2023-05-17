package ru.mirea.structf.data.repository

import ru.mirea.structf.data.dao.DocDao
import ru.mirea.structf.data.model.Doc
import ru.mirea.structf.data.repository.DocRepository

class DocRepositoryImpl (
    private val docDao: DocDao
) : DocRepository {

    override suspend fun insertDoc(doc: Doc) = docDao.insertDoc(doc)

    override suspend fun deleteDoc(doc: Doc) = docDao.deleteDoc(doc)

    override fun getDocsOrderedByName() = docDao.getDocsOrderedByName()

    override suspend fun getDocByName(name: String) = docDao.getDocByName(name)

}