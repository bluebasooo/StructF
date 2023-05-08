package ru.mirea.structf.data

data class DocState(
    val name: String,
    val folder: String,
    val tag: String = "",
    val isTracked: Boolean = false,
    val sortType: SortTypes = SortTypes.NAME

)
