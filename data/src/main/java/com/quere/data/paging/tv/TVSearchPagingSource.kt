package com.quere.data.paging.tv

import com.quere.data.base.BasePagingSource
import com.quere.data.service.tvservice.TVService
import com.quere.domain.model.tv.TVshow
import retrofit2.HttpException
import java.io.IOException

class TVSearchPagingSource(
    private val service: TVService,
    private val query : String?
) : BasePagingSource<TVshow>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVshow> {
        val position = params.key ?: 1

        return try{
            val response = service.getTVshowSeach(
                query?:"",
                1
            )
            val photos = response.results
            var currentList = listOf<TVshow>()

            if(photos.size<20){
                currentList = photos
            } else {
                currentList = photos.subList(0,20)
            }

            LoadResult.Page(
                data = currentList,
                prevKey = if (position == 1 && !(photos.isEmpty())) null else position-1,
                nextKey = if (photos.isEmpty()) null else null
            )
        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        }
    }
}