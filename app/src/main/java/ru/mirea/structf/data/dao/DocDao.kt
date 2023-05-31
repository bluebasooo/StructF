package ru.mirea.structf.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.mirea.structf.data.model.Doc
import ru.mirea.structf.data.model.Tag
import ru.mirea.structf.data.model.TagsWithDocs

@Dao
interface DocDao {

    @Transaction
    @Query("INSERT OR REPLACE INTO doc_table (name, path, tag_id)" +
            "VALUES(:name, :path, :tagId)")
    suspend fun insertDoc(name: String, path: String, tagId: Int)

    @Query("SELECT (SELECT COUNT(*)/1.0 FROM doc_table d INNER JOIN tag_table t USING(tag_id) WHERE t.name = 'UNTRACKED') / (SELECT COUNT(*) FROM doc_table)")
    fun getPercentUntrackedDocs(): LiveData<Double>

    @Query("SELECT * FROM doc_table ORDER BY doc_id DESC LIMIT 6")
    fun getRecentDocs(): LiveData<List<Doc>>

    @Query("SELECT tag_id, end_path, name, color FROM (SELECT t.name, t.color, t.tag_id, t.end_path, COUNT(d.name) as num_of_docs FROM doc_table d INNER JOIN tag_table t USING(tag_id) GROUP BY t.name ORDER BY num_of_docs DESC) LIMIT 3")
    fun getMostPopularTags(): LiveData<List<Tag>>



    @Delete
    suspend fun deleteDoc(doc: Doc)

    @Query("SELECT * FROM doc_table ORDER BY name ASC")
    fun getDocsOrderedByName(): LiveData<List<Doc>>

    @Query("SELECT * FROM doc_table WHERE name = :name")
    suspend fun getDocByName(name: String): Doc?

    @Query("SELECT COUNT(*) FROM doc_table WHERE path = :absolutePath")
    suspend fun getNumOfFilesInCurrentFolder(absolutePath: String): Int

    @Transaction
    @Query("SELECT * FROM tag_table WHERE name =:name")
    suspend fun getTagWithDocs(name: String): List<TagsWithDocs>

    @Transaction
    @Query("SELECT d.doc_id, d.name, d.path, d.tag_id " +
            "FROM doc_table d JOIN tag_table t " +
            "ON d.tag_id = t.tag_id " +
            "WHERE t.name = :tagName")
    suspend fun getDocByTag(tagName: String): List<Doc>


    @Transaction
    @Query("SELECT d.doc_id, d.name, d.path, d.tag_id " +
            "FROM doc_table d JOIN tag_table t " +
            "ON d.tag_id = t.tag_id " +
            "WHERE t.tag_id = :tagId")
    suspend fun getDocByTagId(tagId: Long): List<Doc>

    @Update
    suspend fun update(doc: Doc)

    @Query("SELECT d.doc_id, d.name, d.path, d.tag_id " +
            "FROM doc_table d JOIN tag_table t " +
            "ON d.tag_id = t.tag_id " +
            "WHERE t.name = 'UNTRACKED'")
    fun getUntrackedDocs(): LiveData<List<Doc>>

    @Query("SELECT * FROM doc_table WHERE doc_id = :id")
    suspend fun getDocById(id: Long): Doc?
}