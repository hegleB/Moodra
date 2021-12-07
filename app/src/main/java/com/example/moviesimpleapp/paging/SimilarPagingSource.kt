package com.example.moviesimpleapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesimpleapp.AppConstants
import com.example.moviesimpleapp.retrofit.*
import retrofit2.HttpException
import java.io.IOException


class SimilarPagingSource(
    private val mediaService: MediaService,
    private val id: Int
) : PagingSource<Int, OtherContent>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OtherContent> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = mediaService.geSimilar(
                id,
                AppConstants.api_key,
                AppConstants.language,
                1
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

    override fun getRefreshKey(state: PagingState<Int, OtherContent>): Int? {
        TODO("Not yet implemented")
    }
}