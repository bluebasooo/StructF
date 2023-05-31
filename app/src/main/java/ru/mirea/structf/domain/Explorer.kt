package ru.mirea.structf.domain

import android.os.Environment
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


class Explorer(
    rootFolder: String
) {
    private var currentFolder = File(rootFolder)
    val rootFolder = Environment.getExternalStorageDirectory().absolutePath

    fun createFolder(nameFolder: String) {
        File(currentFolder, nameFolder).mkdir()
    }

    fun getFilesFromCurrentFolder(): List<String> {
        return currentFolder.listFiles()
            .filter { it.isFile }
            .map { it.name }
            .toList()
    }

    fun getFoldersFromCurrentFolder(): List<String> {
        return currentFolder.listFiles()
            .filter { it.isDirectory }
            .map { it.name }
            .toList()
    }
    fun getPath(): String = currentFolder.absolutePath

    fun getName(): String = currentFolder.name

    fun up() {
        if(currentFolder.absolutePath != rootFolder) {
            currentFolder = currentFolder.parentFile ?: currentFolder
        }
    }

    fun down(innerFolder: String) {
        currentFolder = File(currentFolder, innerFolder)
    }

    fun renameFolder(folderName: String, newName: String) {
        File(currentFolder, folderName).renameTo(File(currentFolder, newName))
    }

    fun moveFile(source: String, target: String, name: String) {
        val sourcePath = Paths.get("${source}/${name}")
        val targetPath = Paths.get("${target}/${name}")
        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING)
    }

    fun moveFromCurrent(target: String, name: String) {
        moveFile(currentFolder.absolutePath, target, name)
    }

}