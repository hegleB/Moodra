package com.quere.data.paging.movie

import com.quere.data.base.BasePagingSource
import com.quere.data.service.movieservice.MovieService
import com.quere.domain.model.movie.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MovieSearchAllPagingSource(
    private val service: MovieService,
    private val query : String?
) : BasePagingSource<Movie>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: 1

        return try{
            val response = service.getMovieSeach(
                query?:"",
                position
            )
            val photos = response.results
            var currentList = listOf<Movie>()

            if(photos.size<18){
                currentList = photos
            } else {
                currentList = photos.subList(0,18)
            }
            withContext(Dispatchers.IO) {

                delay(1200)
                LoadResult.Page(
                    data = currentList,
                    prevKey = if (position == 1 && !(photos.isEmpty())) null else position - 1,
                    nextKey = if (photos.isEmpty()) null else position + 1
                )
            }
        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        }
    }
}