package com.quere.data.paging.movie

import com.quere.data.base.BasePagingSource
import com.quere.data.service.movieservice.MovieService
import com.quere.domain.model.movie.Movie
import retrofit2.HttpException
import java.io.IOException

class MovieSearchPagingSource(
    private val service: MovieService,
    private val query : String?
) : BasePagingSource<Movie>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: 1

        return try{
            val response = service.getMovieSeach(
                query?:"",
               1
            )
            val photos = response.results
            var currentList = listOf<Movie>()

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