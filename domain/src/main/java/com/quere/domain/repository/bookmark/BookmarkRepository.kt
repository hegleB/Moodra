package com.quere.domain.repository.bookmark

import com.quere.domain.model.common.Bookmark

interface BookmarkRepository {

    fun getBookmark() : List<Bookmark>
    suspend fun insertBookmark(bookmark: Bookmark)
    suspend fun deleteBookmark(bookmark: Bookmark)
    suspend fun checkBookmark(id: String) : Boolean
    suspend fun deleteAllBookmark()
}