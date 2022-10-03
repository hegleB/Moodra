package com.quere.domain.usecase

import com.quere.domain.model.common.Bookmark
import com.quere.domain.repository.bookmark.BookmarkRepository
import javax.inject.Inject

class DeleteBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {

    suspend fun deleteBookmarkUseCase(bookmark: Bookmark) = bookmarkRepository.deleteBookmark(bookmark)

}