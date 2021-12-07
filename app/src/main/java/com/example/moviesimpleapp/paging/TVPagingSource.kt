package com.example.moviesimpleapp.paging

import com.example.moviesimpleapp.retrofit.TVshow


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesimpleapp.AppConstants
import com.example.moviesimpleapp.retrofit.MediaService
import com.example.moviesimpleapp.retrofit.Movie

import retrofit2.HttpException
import java.io.IOException



class TVPagingSource(
    private val mediaService: MediaService,
    private val type: String
): PagingSource<Int, TVshow>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVshow> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try{
            val response = mediaService.getTV(
                type,
                AppConstants.language,
                AppConstants.api_key)
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if(position== STARTING_PAGE_INDEX) null else position-1,
                nextKey = if(photos.isEmpty()) null else position+1
            )
        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, TVshow>): Int? {
        TODO("Not yet implemented")
    }
}