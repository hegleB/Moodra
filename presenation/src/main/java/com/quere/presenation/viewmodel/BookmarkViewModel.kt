package com.quere.presenation.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quere.domain.model.common.Bookmark
import com.quere.domain.model.common.Detail
import com.quere.domain.usecase.DeleteAllBookarmkUseCase
import com.quere.domain.usecase.DeleteBookmarkUseCase
import com.quere.domain.usecase.GetBookmarkRepoUseCase
import com.quere.domain.usecase.SaveBookmarkUseCase
import com.quere.presenation.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarkRepoUseCase: GetBookmarkRepoUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val saveBookmarkUseCase: SaveBookmarkUseCase,
    private val deleteAllBookarmkUseCase : DeleteAllBookarmkUseCase
) : ViewModel() {

    fun getBookmark() = getBookmarkRepoUseCase.getBookmarkRepoUseCase()
    suspend fun deleteBookamrk(bookmark: Bookmark) = deleteBookmarkUseCase.deleteBookmarkUseCase(bookmark)
    suspend fun deleteAllBookmark() = deleteAllBookarmkUseCase.deleteBookarmk()
    suspend fun saveBookmark(bookmark: Bookmark) = saveBookmarkUseCase.saveBookmarkUseCase(bookmark)

    private val _bookmarkEdit : MutableLiveData<Event<Unit>> = MutableLiveData()
    val bookmarkEdit : LiveData<Event<Unit>>
        get() = _bookmarkEdit

    private val _bookmarkSubmit : MutableLiveData<Event<Unit>> = MutableLiveData()
    val bookmarkSubmit : LiveData<Event<Unit>>
        get() = _bookmarkSubmit

    private val _bookmarkDeleteAll : MutableLiveData<Event<Unit>> = MutableLiveData()
    val bookmarkDeleteAll : LiveData<Event<Unit>>
        get() = _bookmarkDeleteAll

    private val _bookmarkDeleteSelected : MutableLiveData<Event<Unit>> = MutableLiveData()
    val bookmarkDeleteSelected : LiveData<Event<Unit>>
        get() = _bookmarkDeleteSelected

    private val _isSelected : MutableLiveData<Boolean> = MutableLiveData(false)
    val isSelected : LiveData<Boolean>
        get() = _isSelected

    private val _savedBookmarkList : MutableLiveData<List<Bookmark>> = MutableLiveData()
    val savedBookmarkList : LiveData<List<Bookmark>> = _savedBookmarkList

    private val _selectBookmark : MutableLiveData<MutableList<Bookmark>> = MutableLiveData()
    val selectBookmark : LiveData<MutableList<Bookmark>> = _selectBookmark

    fun showEdit() {
        _bookmarkEdit.value = Event(Unit)
    }

    fun showSubmit() {
        _bookmarkSubmit.value = Event(Unit)
    }

    fun showDeleteAll() {
        _bookmarkDeleteAll.value = Event(Unit)
    }

    fun showDeleteSelected() {
        _bookmarkDeleteSelected.value = Event(Unit)
    }

    fun setSelected(isSelected : Boolean) {
        _isSelected.value = isSelected
    }

    fun getSavedBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            _savedBookmarkList.postValue(getBookmark())
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllBookmark()
            _savedBookmarkList.postValue(listOf())
        }
    }

    fun deleteSelectedBookmark(bookmark: Bookmark) {

        viewModelScope.launch(Dispatchers.IO) {
            deleteBookamrk(bookmark)
            _savedBookmarkList.postValue(getBookmark())
        }

    }

    fun selectBookmarkList(bookmarkList : MutableList<Bookmark>) {
        _selectBookmark.value = bookmarkList

    }
}