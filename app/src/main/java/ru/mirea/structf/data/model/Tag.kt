package ru.mirea.structf.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tag(
    @PrimaryKey
    val id: Int,
    val name: String,
    val color: String
)
