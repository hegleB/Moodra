package com.quere.presenation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.quere.domain.model.common.*
import com.quere.domain.model.movie.Movie
import com.quere.domain.model.tv.TVshow
import com.quere.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSimilarRepoUseCase: GetSimilarRepoUseCase,
    private val getRecommendRepoUseCase : GetRecommendRepoUseCase,
    private val getMovieDetailRepoUseCase : GetMovieDetailRepoUseCase,
    private val getTrailerRepoUseCase: GetTrailerRepoUseCase,
    private val getCreditRepoUseCase : GetCreditRepoUseCase,
    private val getTVDetailRepoUseCase : GetTVDetailRepoUseCase
) : ViewModel() {

    suspend fun getMovieDetail(id: Int) : Movie { return getMovieDetailRepoUseCase.getMovieDetail(
        id)!!}

    suspend fun getTVDetail(id: Int) : TVshow { return getTVDetailRepoUseCase.getTVDetail(
        id)!!}

    suspend fun getCredit(type:String,id: Int) : Credit { return getCreditRepoUseCase.getCredit(type,
        id)!!}

    suspend fun getTrailer(type:String, id: Int) : Trailers { return getTrailerRepoUseCase.getTrailer(type,id)!!}

    suspend fun getsimilar(type:String,id: Int) : Flow<PagingData<OtherContent>> { return getSimilarRepoUseCase.getSimilar(
        type,id)!!.cachedIn(viewModelScope)}

    suspend fun getrecommend(type:String,id: Int) : Flow<PagingData<OtherContent>> { return getRecommendRepoUseCase.getRecommend(
        type,id)!!.cachedIn(viewModelScope)}

}