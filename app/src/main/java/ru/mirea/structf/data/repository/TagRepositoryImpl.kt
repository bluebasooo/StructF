package ru.mirea.structf.data.repository

import ru.mirea.structf.data.dao.TagDao
import ru.mirea.structf.data.model.Tag

class TagRepositoryImpl(
    private val tagDao: TagDao
) {
    suspend fun insertTag(tag: Tag) = tagDao.insertTag(tag)

    suspend fun deleteTag(tag: Tag) = tagDao.deleteTag(tag)

    fun getTagsOrderedByName() = tagDao.getTagsOrderedByName()

    suspend fun getTagByName(tag: String) = tagDao.getTagByName(tag)

}