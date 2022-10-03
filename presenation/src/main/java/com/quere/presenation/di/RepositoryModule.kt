package com.quere.presenation.di

import com.quere.data.database.BookmarkDao
import com.quere.data.repository.local.BookmarkRepositoryImpl
import com.quere.data.repository.remote.common.CommonRepositoryImpl
import com.quere.data.repository.remote.movie.MovieRepositoryImpl
import com.quere.data.repository.remote.search.SearchRepositoryImpl
import com.quere.data.repository.remote.tv.TVshowRepositoryImpl
import com.quere.data.service.commonservice.CommonService
import com.quere.data.service.movieservice.MovieService
import com.quere.data.service.tvservice.TVService
import com.quere.domain.repository.bookmark.BookmarkRepository
import com.quere.domain.repository.common.CommonRepository
import com.quere.domain.repository.movie.MovieRepository
import com.quere.domain.repository.search.SearchRepository
import com.quere.domain.repository.tv.TVshowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMovieRepository(
        movieService: MovieService
    ) : MovieRepository {
        return MovieRepositoryImpl(
            movieService
        )
    }

    @Provides
    fun provideTVshowRepository(
        tvService: TVService
    ) : TVshowRepository {
        return TVshowRepositoryImpl(
            tvService
        )
    }

    @Provides
    fun provideCommonRepository(
        commonService: CommonService
    ) : CommonRepository {
        return CommonRepositoryImpl(
            commonService
        )
    }

    @Provides
    fun provideBookmarkRepository(
        bookmarkDao: BookmarkDao
    ) : BookmarkRepository {
        return BookmarkRepositoryImpl(
            bookmarkDao
        )
    }

    @Provides
    fun provideSearchRepository(
        movieService: MovieService,
        tvService: TVService
    ) : SearchRepository {
        return SearchRepositoryImpl(
            movieService,
            tvService
        )
    }
}