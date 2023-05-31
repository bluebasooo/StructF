package ru.mirea.structf.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class TagsWithDocs(
    @Embedded val tag: Tag,
    @Relation(
        parentColumn = "tag_id",
        entityColumn = "tag_id"
    )
    val doc: List<Doc>
)
