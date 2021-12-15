package com.quere.moodra.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.quere.moodra.paging.*
import com.quere.moodra.retrofit.*
import com.quere.moodra.room.Bookmark
import com.quere.moodra.room.BookmarkDao

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class Repository @Inject constructor(
    private val mediaService: MediaService,
    private val bookmarkDao: BookmarkDao
) {


    fun getMoviesData(moveType: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { MoviePagingSource(mediaService, moveType) }
        ).flow
    }


    fun getTvData(tvType: String): Flow<PagingData<TVshow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { TVPagingSource(mediaService, tvType) }
        ).flow
    }

    fun getMovieSearchData(query: String): Flow<PagingData<MovieSearch>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 200,
                enablePlaceholders = false,
                initialLoadSize = 18,

                ),
            pagingSourceFactory = { MovieSearchPagingSource(mediaService, query) }
        ).flow
    }

    fun getMovieSearchDetaileData(query: String): Flow<PagingData<MovieSearch>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 200,
                enablePlaceholders = false,
                initialLoadSize = 9

                ),
            pagingSourceFactory = { MovieSearchDetailPagingSource(mediaService, query) }
        ).flow
    }

    fun getTVSearchData(query: String): Flow<PagingData<TVshowSearch>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false

                ),
            pagingSourceFactory = { TVSearchPagingSource(mediaService, query) }
        ).flow
    }

    fun getTVSearchDetailData(query: String): Flow<PagingData<TVshowSearch>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 9

                ),
            pagingSourceFactory = { TVSearchDetailPagingSource(mediaService, query) }
        ).flow
    }

    fun getMovieGenre(movie_genre: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,

                ),
            pagingSourceFactory = { MovieGenrePagingSource(mediaService, movie_genre) }
        ).flow
    }

    fun getMovieGenreDetail(movie_genre: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 9

                ),
            pagingSourceFactory = { MovieGenreDetailPagingSource(mediaService, movie_genre) }
        ).flow
    }


    fun getTvGenre(tv_genre: String): Flow<PagingData<TVshow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,

                ),
            pagingSourceFactory = { TvGenrePagingSource(mediaService, tv_genre) }
        ).flow
    }

    fun getTvGenreDetail(tv_genre: String): Flow<PagingData<TVshow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 9

                ),
            pagingSourceFactory = { TVGenreDetailPagingSource(mediaService, tv_genre) }
        ).flow
    }

    fun getSimilar(id: Int): Flow<PagingData<OtherContent>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,


                ),
            pagingSourceFactory = { SimilarPagingSource(mediaService, id) }
        ).flow
    }

    fun getRecommend(id: Int): Flow<PagingData<OtherContent>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
               

                ),
            pagingSourceFactory = { RecommendPagingSourcec(mediaService, id) }
        ).flow
    }

    suspend fun getMovieDetailData(id: Int, language: String, api_key: String) : MovieDetail {

        return mediaService.getMovieDetail(id, language, api_key)

    }

    suspend fun getTVDetailData(id: Integer, language: String, api_key: String) : TVDetail {

        return mediaService.getTVDetail(id, language, api_key)

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

    fun getTVTrailer(id: Int): Flow<PagingData<Trailer>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 10,

                ),
            pagingSourceFactory = { TVTrailerPagingSource(mediaService, id) }
        ).flow
    }

    suspend fun getMovieCreditData(movieId: Integer, api_key: String, language: String): Credit {
        return mediaService.getMovieCredit(movieId, api_key, language)
    }


    suspend fun getTVCreditData(tvId: Integer, api_key: String, language: String): Credit {
        return mediaService.getTVCredit(tvId, api_key, language)
    }


    suspend fun insert(bookmark: Bookmark) = bookmarkDao.insert(bookmark)


    suspend fun delete(bookmark: Bookmark) {
        bookmarkDao.delete(bookmark)
    }

    suspend fun deleteAll() {
        bookmarkDao.deleteAll()
    }

    fun getBookmark(): LiveData<List<Bookmark>> = bookmarkDao.getBookmark()

    suspend fun check(id: String) = bookmarkDao.checkBookmark(id)


}
