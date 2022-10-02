package com.quere.domain.usecase

import com.quere.domain.repository.bookmark.BookmarkRepository
import javax.inject.Inject

class CheckBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {

    suspend fun checkBookmark(id : String) = bookmarkRepository.checkBookmark(id)
}