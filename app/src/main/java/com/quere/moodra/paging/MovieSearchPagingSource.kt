package com.quere.moodra.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.quere.moodra.AppConstants
import com.quere.moodra.retrofit.MediaService
import com.quere.moodra.retrofit.Movie
import com.quere.moodra.retrofit.MovieSearch
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception


const val STARTING_PAGE_INDEX = 1

class MovieSearchPagingSource(
    private val mediaService: MediaService,
    private val query: String
) : PagingSource<Int, MovieSearch>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieSearch> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = mediaService.getMovieSeach(
                AppConstants.API_KEY,
                AppConstants.LANGUAGE,
                query,
                Integer(1)
            )
            val photos = response.results
            var currentList = listOf<MovieSearch>()

            if(photos.size<18){
                currentList = photos
            } else {
                currentList = photos.subList(0,18)
            }

            LoadResult.Page(
                data = currentList,
                prevKey = if (position == STARTING_PAGE_INDEX && !(photos.isEmpty())) null else position-1,
                nextKey = if (photos.isEmpty()) null else null
            )


        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, MovieSearch>): Int? {
        TODO("Not yet implemented")
    }
}