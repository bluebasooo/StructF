package ru.mirea.structf.ui.viewmodels

import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mirea.structf.data.model.Doc
import ru.mirea.structf.data.repository.CloudRepositoryImpl
import ru.mirea.structf.data.repository.DocRepositoryImpl
import ru.mirea.structf.domain.Explorer
import java.io.File
import java.text.FieldPosition
import javax.inject.Inject

@HiltViewModel
class DocViewModel @Inject constructor(
    private val repository: DocRepositoryImpl,
    private val cloudRepository: CloudRepositoryImpl
): ViewModel() {

    private val docs = MutableLiveData<List<Doc>>()
    private var exp = Explorer(Environment.getExternalStorageDirectory().toString())

    fun getDocs(): LiveData<List<Doc>> = docs


    fun insertDoc(doc: Doc) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertDoc(doc)
        }
    }

    fun getDocsByTag(tagName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            docs.postValue(repository.getDocByTag(tagName))
        }
    }

    fun cloudUpload(doc: Doc) {
        viewModelScope.launch(Dispatchers.IO) {
            cloudRepository.upload(
                doc.name,
                Uri.fromFile(File(doc.path, doc.name))
            ).addOnSuccessListener {
                insertDoc(doc)
            }
        }
    }

    fun getDocsByTagId(tagId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            docs.postValue(repository.getDocByTagId(tagId))
        }
    }

    fun updateTagForDoc(id: Long, tagId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDocById(id, tagId)
        }
    }

    fun moveFromCurrent(target: String, name: String) {
        exp.moveFromCurrent(target, name)
    }

    fun moveDoc(docId: Long, tagId: Int,source: String, target:String, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            exp.moveFile(source, target, name)
            repository.updatePathAndTagForDoc(docId, tagId, target)
        }
    }



    fun updatePathForDoc(docId: Long, endPath: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePathForDoc(docId, endPath)
        }
    }

    fun updateTagAndTagForDoc(docId: Long, tagId: Int, endPath: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePathAndTagForDoc(docId, tagId, endPath)
        }
    }
}