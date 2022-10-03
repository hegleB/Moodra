package com.quere.domain.repository.search

import androidx.paging.PagingData
import com.quere.domain.model.movie.Movie
import com.quere.domain.model.tv.TVshow
import kotlinx.coroutines.flow.Flow


interface SearchRepository {

    suspend fun getMovieSearch(query: String) : Flow<PagingData<Movie>>
    suspend fun getTVSearch(query: String) : Flow<PagingData<TVshow>>
    suspend fun getMovieSearchAll(query: String) : Flow<PagingData<Movie>>
    suspend fun getTVSearchAll(query: String) : Flow<PagingData<TVshow>>

}