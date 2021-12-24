package com.quere.moodra.repository

import androidx.lifecycle.LiveData
import com.quere.moodra.room.Bookmark
import com.quere.moodra.room.BookmarkDao
import javax.inject.Inject

class BookmarkRepository @Inject constructor(
    private val bookmarkDao: BookmarkDao
) {

    suspend fun insert(bookmark: Bookmark) = bookmarkDao.insert(bookmark)


    suspend fun delete(bookmark: Bookmark) {
        bookmarkDao.delete(bookmark)
    }

    suspend fun deleteAll() {
        bookmarkDao.deleteAll()
    }

    fun getBookmark(): LiveData<List<Bookmark>> = bookmarkDao.getBookmark()

    suspend fun check(id: String) = bookmarkDao.checkBookmark(id)

}