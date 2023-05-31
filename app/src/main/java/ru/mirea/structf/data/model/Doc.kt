package ru.mirea.structf.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "doc_table",
        indices = [Index(value = ["name", "path"], unique = true)]
)
data class Doc(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "doc_id")
    val docId: Long = 0,
    val name: String,
    val path: String,
    @ColumnInfo(name = "tag_id")
    val tagId: Int = 1
)
