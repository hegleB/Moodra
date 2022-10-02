package com.quere.data.repository.local

import androidx.lifecycle.LiveData
import com.quere.data.database.BookmarkDao
import com.quere.domain.model.common.Bookmark
import com.quere.domain.model.common.Detail
import com.quere.domain.repository.bookmark.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkRepository {

    override fun getBookmark(): List<Bookmark> {
        return bookmarkDao.getBookmark()
    }

    override suspend fun insertBookmark(bookmark: Bookmark) {
        return bookmarkDao.insert(bookmark)
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        return bookmarkDao.delete(bookmark)
    }

    override suspend fun checkBookmark(id: String) : Boolean {
        return bookmarkDao.checkBookmark(id)!=null
    }

    override suspend fun deleteAllBookmark() {
        return bookmarkDao.deleteAll()
    }
}