package com.example.moviesimpleapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesimpleapp.AppConstants
import com.example.moviesimpleapp.retrofit.MediaService
import com.example.moviesimpleapp.retrofit.Movie
import com.example.moviesimpleapp.retrofit.TVshow
import retrofit2.HttpException
import java.io.IOException

class TVGenreDetailPagingSource(
    private val mediaService: MediaService,
    private val type: String
) : PagingSource<Int, TVshow>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVshow> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = mediaService.getTVGenre(
                AppConstants.api_key,
                AppConstants.language,
                type,
                Integer(position)
            )
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position-1,
                nextKey = if (photos.isEmpty()) null else position+1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, TVshow>): Int? {
        TODO("Not yet implemented")
    }
}