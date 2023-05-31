package ru.mirea.structf.data.repository

import androidx.room.Transaction
import ru.mirea.structf.data.dao.DocDao
import ru.mirea.structf.data.model.Doc

class DocRepositoryImpl (
    private val docDao: DocDao
) {

    suspend fun insertDoc(doc: Doc) = docDao.insertDoc(doc.name, doc.path, doc.tagId)

    suspend fun deleteDoc(doc: Doc) = docDao.deleteDoc(doc)

    fun getDocsOrderedByName() = docDao.getDocsOrderedByName()

    fun getUntrackedDocs() = docDao.getUntrackedDocs()

    suspend fun getDocByName(name: String) = docDao.getDocByName(name)

    suspend fun getDocById(id: Long) = docDao.getDocById(id)

    fun getPercent() = docDao.getPercentUntrackedDocs()
    fun getRecent() = docDao.getRecentDocs()

    fun getPopular() = docDao.getMostPopularTags()

    @Transaction
    suspend fun insertBunch(docs: List<Doc>) = docs.forEach { insertDoc(it) }
    suspend fun getNumOfFilesInCurrentFolder(absolutePath: String) = docDao.getNumOfFilesInCurrentFolder(absolutePath)

    suspend fun getDocByTag(tagName: String): List<Doc> = docDao.getDocByTag(tagName)
    suspend fun getDocByTagId(tagId: Long): List<Doc> = docDao.getDocByTagId(tagId)

    suspend fun updateDocById(id: Long, tagId: Int) {
        val doc = docDao.getDocById(id)!!
        docDao.update(Doc(
            docId = id,
            name = doc.name,
            path = doc.path,
            tagId = tagId
        ))
    }

    @Transaction
    suspend fun updatePathForDoc(id: Long, endPath: String) {
        val doc = docDao.getDocById(id)!!
        docDao.update(Doc(
            docId = id,
            name = doc.name,
            path = endPath,
            tagId = doc.tagId
        ))
    }

    @Transaction
    suspend fun updatePathAndTagForDoc(id: Long, tagId: Int, endPath: String) {
        val doc = docDao.getDocById(id)!!
        docDao.update(Doc(
            docId = id,
            name = doc.name,
            path = endPath,
            tagId = tagId
        ))
    }

}