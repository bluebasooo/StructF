package ru.mirea.structf.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doc_table")
data class Doc(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val folderId: Int,
    val tagId: Int,
    val isTracked: Boolean
)
