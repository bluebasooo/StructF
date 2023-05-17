package ru.mirea.structf.ui.viewmodels

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mirea.structf.data.repository.DocRepositoryImpl
import ru.mirea.structf.data.model.Doc
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DocViewModel @Inject constructor(
    private val repository: DocRepositoryImpl
): ViewModel() {

    val allDocs: LiveData<List<Doc>> = repository.getDocsOrderedByName()


    fun insertDoc(doc: Doc) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertDoc(doc)
        }
    }

    fun expectAllFiles() {
        viewModelScope.launch(Dispatchers.IO) {
            File(Environment.getExternalStorageDirectory().toString() + "/Download").walk().forEach {
                repository.insertDoc(
                    Doc(
                        name=it.name,
                        folderId = 0,
                        tagId = 0,
                        isTracked=true
                    )
                )
            }
        }
    }
}