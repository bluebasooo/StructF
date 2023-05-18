package ru.mirea.structf.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import ru.mirea.structf.data.model.Tag

@Dao
interface TagDao {

    @Upsert
    suspend fun insertTag(tag: Tag)

    @Delete
    suspend fun deleteTag(tag: Tag)

    @Query("SELECT * FROM tag_table ORDER BY name ASC")
    fun getTagsOrderedByName(): LiveData<List<Tag>>

    @Query("SELECT * FROM tag_table WHERE name = :name")
    suspend fun getTagByName(name:String): Tag?

}