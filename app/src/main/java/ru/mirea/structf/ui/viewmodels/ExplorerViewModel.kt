package ru.mirea.structf.ui.viewmodels

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mirea.structf.data.model.Tag
import ru.mirea.structf.data.repository.TagRepositoryImpl
import ru.mirea.structf.domain.Explorer
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ExplorerViewModel @Inject constructor(
    private val repository: TagRepositoryImpl
): ViewModel() {
    private var exp = Explorer(Environment.getExternalStorageDirectory().toString())
    private val listOfFiles = MutableLiveData(exp.getFoldersFromCurrentFolder())

    fun getFolders(): LiveData<List<String>> = listOfFiles
    fun getName() = exp.getName()
    fun downPath() = exp.getPath()

    fun goDown(folder: String) {
        viewModelScope.launch(Dispatchers.IO) {
            exp.down(folder)
            listOfFiles.postValue(exp.getFoldersFromCurrentFolder())
        }
    }

    fun goUp() {
        viewModelScope.launch(Dispatchers.IO) {
            exp.up()
            listOfFiles.postValue(exp.getFoldersFromCurrentFolder())
        }
    }

    fun goStart() {
        viewModelScope.launch(Dispatchers.IO) {
            exp = Explorer(Environment.getExternalStorageState().toString())
            listOfFiles.postValue(exp.getFoldersFromCurrentFolder())
        }
    }

    fun createFolder(folder: String) {
        viewModelScope.launch(Dispatchers.IO) {
            exp.createFolder(folder)
            listOfFiles.postValue(exp.getFoldersFromCurrentFolder())
        }
    }

    fun changeFolderName(folder: String) {

    }

    fun createHierarchyAboutTag(tag: Tag) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTag(tag)
        }
    }

    fun renameFolder(name: String, newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            exp.renameFolder(name, newName)
            listOfFiles.postValue(exp.getFoldersFromCurrentFolder())
        }
    }



}