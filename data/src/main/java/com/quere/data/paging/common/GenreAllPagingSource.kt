package com.quere.data.paging.common

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.quere.data.service.commonservice.CommonService
import com.quere.data.service.movieservice.MovieService
import com.quere.domain.model.common.Detail
import com.quere.domain.model.movie.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class GenreAllPagingSource(
    private val commonService: CommonService,
    private val type: String,
    private val genre : String
): PagingSource<Int, Detail>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Detail> {
        val position = params.key ?: 1

        return try{
            val response = commonService.getGenre(type,genre,position)
            val result = response.results
            var currentList = listOf<Detail>()

            if(result.size<18){
                currentList = result
            } else {
                currentList = result.subList(0,18)
            }
            withContext(Dispatchers.IO) {

                delay(1200)

                LoadResult.Page(
                    data = currentList,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (response.results.isEmpty()) null else position + 1
                )
            }
        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Detail>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    }
}