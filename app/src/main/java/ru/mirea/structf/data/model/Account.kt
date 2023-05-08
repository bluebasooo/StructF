package ru.mirea.structf.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey
    val id: Int,
    val login: String,
    val password: String,
    val service: String
)
