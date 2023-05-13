package ru.mirea.structf.domain

import java.io.File


class Explorer(
    rootFolder: String
) {
    private var currentFolder = File(rootFolder)


    fun getFilesFromCurrentFolder(): List<String> {
        return currentFolder.list()?.toList() ?: listOf()
    }

    fun up() {
        currentFolder = currentFolder.parentFile ?: currentFolder
    }

    fun down(innerFolder: String) {
        currentFolder = File(currentFolder.absolutePath + "/" + innerFolder)
    }

}