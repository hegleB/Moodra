package com.quere.domain.usecase

import com.quere.domain.repository.bookmark.BookmarkRepository
import javax.inject.Inject

class GetBookmarkRepoUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {

    fun getBookmarkRepoUseCase() = bookmarkRepository.getBookmark()
}