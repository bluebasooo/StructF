package ru.mirea.structf.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag_table")
data class Tag(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val color: String
)
