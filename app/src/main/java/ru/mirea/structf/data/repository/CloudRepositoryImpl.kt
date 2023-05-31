package ru.mirea.structf.data.repository

import android.net.Uri
import android.os.Environment
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.File

class CloudRepositoryImpl(
    private var storageRef : StorageReference
) {

    private val path = Environment.getExternalStorageDirectory().toString() + "/Cloud"

    fun setUserStorage(userName: String) {
        storageRef = storageRef.child(userName)
    }

    fun upload(fileName:String, file: Uri): UploadTask  {
        return storageRef.child(fileName).putFile(file)
    }

    fun download(fileName:String, firestorePath: String): FileDownloadTask {
        return storageRef.child(firestorePath).getFile(
            File.createTempFile("${path}/${fileName.substringBeforeLast(".")}",
                fileName.substringAfterLast(".")
            )
        )
    }

}