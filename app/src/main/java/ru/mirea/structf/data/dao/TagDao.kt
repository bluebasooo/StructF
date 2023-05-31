package ru.mirea.structf.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.mirea.structf.data.model.Tag

@Dao
interface TagDao {

    @Transaction
    @Query("INSERT OR IGNORE INTO tag_table (name, end_path, color)" +
            "VALUES(:name, :endPath, :color)")
    suspend fun insertTag(name: String, endPath: String, color: String)

    @Delete
    suspend fun deleteTag(tag: Tag)

    @Query("SELECT * FROM tag_table ORDER BY name ASC")
    fun getTagsOrderedByName(): LiveData<List<Tag>>

    @Query("SELECT * FROM tag_table WHERE name = :name")
    suspend fun getTagByName(name:String): Tag?

}