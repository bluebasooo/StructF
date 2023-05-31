package ru.mirea.structf.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tag_table",
    indices = [
        Index(value = ["name"], unique = true),
        Index(value = ["end_path"], unique = true)
    ]
)
data class Tag(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tag_id")
    val tagId: Int = 0,
    @ColumnInfo(name = "end_path")
    val endPath: String,
    val name: String,
    val color: String
)
