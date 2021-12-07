package com.example.moviesimpleapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesimpleapp.AppConstants
import com.example.moviesimpleapp.retrofit.MediaService
import com.example.moviesimpleapp.retrofit.OtherContent
import com.example.moviesimpleapp.retrofit.Trailer
import retrofit2.HttpException
import java.io.IOException

class MovieTrailerPagingSource(
    private val mediaService: MediaService,
    private val id: Int
) : PagingSource<Int, Trailer>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Trailer> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = mediaService.getMovieTrailer(
                id,
                AppConstants.api_key,
                AppConstants.language
            )
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else null,
                nextKey = if (photos.isEmpty()) null else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Trailer>): Int? {
        TODO("Not yet implemented")
    }
}