package ru.mirea.structf.ui.viewmodels

import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mirea.structf.data.model.Doc
import ru.mirea.structf.data.model.Tag
import ru.mirea.structf.data.repository.DocRepositoryImpl
import ru.mirea.structf.data.repository.TagRepositoryImpl
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DocRepositoryImpl,
    private val repository2: TagRepositoryImpl
): ViewModel() {

    val percent = repository.getPercent()
    val recent = repository.getRecent()
    val popular = repository.getPopular()



    fun checkUpdatesAndSaveThem() {
        viewModelScope.launch(Dispatchers.IO) {
            repository2.insertTag(
                Tag(name = "UNTRACKED", endPath = Environment.getExternalStoragePublicDirectory("Downloads").absolutePath, color = "Green")
            )
            setUpCloud()
            File(Environment.getExternalStorageDirectory(),"Download")
                .walkTopDown()
                .filter { it.isDirectory }
                .forEach { saveBunch(it) }
        }
    }

    suspend fun setUpCloud() {
        File(Environment.getExternalStorageDirectory().toString(), "Cloud").also {
            if(!it.exists()) {
                it.mkdir()
            }
        }
        repository2.insertTag(
            Tag(name = "CLOUD", endPath = "${Environment.getExternalStorageState().toString()}/Cloud", color = "Pink")
        )
    }

    private fun saveBunch(dir: File) {
        viewModelScope.launch(Dispatchers.IO) {
            val filesInFolder = dir.listFiles().filter { it.isFile }
            val filesTracked = repository.getNumOfFilesInCurrentFolder(dir.absolutePath)
            val isNotFolderTracked = filesInFolder.size != filesTracked
            if(isNotFolderTracked) {
                repository.insertBunch(
                    filesInFolder.map { Doc(name = it.name, path = it.canonicalPath.substringBeforeLast('/')) }
                )
            }
        }
    }

}