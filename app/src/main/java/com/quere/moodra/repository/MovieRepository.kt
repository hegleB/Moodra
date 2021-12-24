package com.quere.moodra.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.quere.moodra.paging.*
import com.quere.moodra.retrofit.*
import com.quere.moodra.room.Bookmark
import com.quere.moodra.room.BookmarkDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MovieRepository @Inject constructor(
    private val mediaService: MediaService
) {


    fun getMoviesData(moveType: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { MoviePagingSource(mediaService, moveType) }
        ).flow
    }

    fun getMovieSearchData(query: String): Flow<PagingData<MovieSearch>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10

            ),
            pagingSourceFactory = { MovieSearchPagingSource(mediaService, query) }
        ).flow
    }

    fun getMovieSearchDetaileData(query: String): Flow<PagingData<MovieSearch>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false

            ),
            pagingSourceFactory = {
                MovieSearchDetailPagingSource(mediaService, query) }
        ).flow

    }




    fun getMovieGenre(movie_genre: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10

                ),
            pagingSourceFactory = { MovieGenrePagingSource(mediaService, movie_genre) }
        ).flow
    }

    fun getMovieGenreDetail(movie_genre: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10

            ),
            pagingSourceFactory = { MovieGenreDetailPagingSource(mediaService, movie_genre) }
        ).flow
    }


    suspend fun getMovieDetailData(id: Int, language: String, api_key: String): MovieDetail {

        return mediaService.getMovieDetail(id, language, api_key)

    }


    fun getMovieTrailer(id: Int): Flow<PagingData<Trailer>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10,

                ),
            pagingSourceFactory = { MovieTrailerPagingSource(mediaService, id) }
        ).flow
    }


    suspend fun getMovieCreditData(movieId: Integer, api_key: String, language: String): Credit {
        return mediaService.getMovieCredit(movieId, api_key, language)
    }




}
