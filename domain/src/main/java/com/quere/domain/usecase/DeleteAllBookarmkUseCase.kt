package com.quere.domain.usecase

import com.quere.domain.repository.bookmark.BookmarkRepository
import javax.inject.Inject

class DeleteAllBookarmkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend fun deleteBookarmk() = bookmarkRepository.deleteAllBookmark()
}