package ru.mirea.structf.data

import ru.mirea.structf.data.model.Doc

sealed interface DocEvent {
    object TrackDoc: DocEvent
    data class SetTag(val tagName: String): DocEvent
    data class SetName(val name: String): DocEvent
    data class SetFolder(val folderName: String): DocEvent
    object ShowDialog: DocEvent
    object HideDiolog: DocEvent
    data class SortFiles(val sortType: SortTypes): DocEvent
    data class DeleteDoc(val doc: Doc): DocEvent
}