package com.quere.moodra.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.quere.moodra.AppConstants
import com.quere.moodra.retrofit.MediaService
import com.quere.moodra.retrofit.TVshowSearch
import retrofit2.HttpException
import java.io.IOException



class TVSearchDetailPagingSource(
    private val mediaService: MediaService,
    private val query: String
): PagingSource<Int, TVshowSearch>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVshowSearch> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try{
            val response = mediaService.getTVshowSeach(AppConstants.api_key,AppConstants.language, query, Integer(position))
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

    override fun getRefreshKey(state: PagingState<Int, TVshowSearch>): Int? {
        TODO("Not yet implemented")
    }
}