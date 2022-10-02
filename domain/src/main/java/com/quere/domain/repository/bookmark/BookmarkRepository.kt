package com.quere.domain.repository.bookmark



import androidx.lifecycle.LiveData
import com.quere.domain.model.common.Bookmark
import kotlinx.coroutines.flow.Flow


interface BookmarkRepository {

    fun getBookmark() : List<Bookmark>
    suspend fun insertBookmark(bookmark: Bookmark)
    suspend fun deleteBookmark(bookmark: Bookmark)
    suspend fun checkBookmark(id: String) : Boolean
    suspend fun deleteAllBookmark()
}