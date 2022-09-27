package com.quere.presenation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.quere.domain.model.common.Detail
import com.quere.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val getGenreRepoUseCase: GetGenreRepoUseCase,
    private val getGenrePopularRepoUseCase: GetGenrePopularRepoUseCase
) : ViewModel() {

    suspend fun getGenre(type:String,genre:String) = getGenreRepoUseCase.getGenre(type,genre)
    suspend fun getGenrePopular(type:String) : Flow<PagingData<Detail>> { return getGenrePopularRepoUseCase.getGenrePopular(
        type)!!.cachedIn(viewModelScope)}

}