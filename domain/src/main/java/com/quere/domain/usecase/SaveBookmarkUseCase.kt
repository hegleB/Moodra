package com.quere.domain.usecase

import com.quere.domain.model.common.Bookmark
import com.quere.domain.repository.bookmark.BookmarkRepository
import javax.inject.Inject

class SaveBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {

    suspend fun saveBookmarkUseCase(bookmark: Bookmark) = bookmarkRepository.insertBookmark(bookmark)

}