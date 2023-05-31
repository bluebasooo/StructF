package ru.mirea.structf.ui.viewmodels

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mirea.structf.data.model.Tag
import ru.mirea.structf.data.repository.TagRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val repository: TagRepositoryImpl
): ViewModel() {
    val allTags: LiveData<List<Tag>> = repository.getTagsOrderedByName()

    fun insertTag(tag: Tag) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTag(tag)
        }
    }

}