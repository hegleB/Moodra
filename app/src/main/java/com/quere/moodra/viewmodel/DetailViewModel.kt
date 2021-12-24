package com.quere.moodra.viewmodel


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.quere.moodra.AppConstants
import com.quere.moodra.repository.BookmarkRepository
import com.quere.moodra.repository.DetailRepository
import com.quere.moodra.repository.MovieRepository
import com.quere.moodra.repository.TVRepository
import com.quere.moodra.retrofit.*
import com.quere.moodra.room.Bookmark
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class DetailViewModel @ViewModelInject constructor(
    private val detailRepo: DetailRepository,
    private val movieRepo: MovieRepository,
    private val tvRepo: TVRepository,
    private val bookmarkRepo : BookmarkRepository

) : ViewModel() {

    val error = MutableLiveData<String>()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _isSeleted = MutableLiveData<Boolean>()

    val isSeleted : LiveData<Boolean> get() = _isSeleted

    fun setSeleted(isSelected: Boolean) {
        _isSeleted.value = isSelected
    }

    var movie_detail_data = MutableLiveData<MovieDetail>()
    var tv_detail_data = MutableLiveData<TVDetail>()

    var movie_credit_data = MutableLiveData<List<Actor>>()
    var tv_credit_data = MutableLiveData<List<Actor>>()

    fun getMovieDetailData(movieId: Int): MutableLiveData<MovieDetail> {
        scope.launch(Dispatchers.IO) {
            val getMovieDetailData =
                movieRepo.getMovieDetailData(movieId, AppConstants.LANGUAGE, AppConstants.API_KEY,)
            try {
                movie_detail_data.postValue(getMovieDetailData)

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    error
                }
            }

        }

        return movie_detail_data

    }

    fun getTVDetailData(tvId: Integer): MutableLiveData<TVDetail> {
        scope.launch(Dispatchers.IO) {
            val getTVDetailData =
                tvRepo.getTVDetailData(tvId, AppConstants.LANGUAGE, AppConstants.API_KEY,)
            try {
                tv_detail_data.postValue(getTVDetailData)

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    error
                }
            }

        }

        return tv_detail_data

    }

    fun getMovieCredit(movieId: Integer): MutableLiveData<List<Actor>> {

        scope.launch(Dispatchers.IO) {
            val getMovieCredit =
                movieRepo.getMovieCreditData(movieId, AppConstants.API_KEY, AppConstants.LANGUAGE)
            try {
                movie_credit_data.postValue(getMovieCredit.cast)
            } catch (e: Exception) {
                error
            }
        }
        return movie_credit_data
    }

    fun getTVCredit(tvId: Integer): MutableLiveData<List<Actor>> {

        scope.launch(Dispatchers.IO) {
            val getTVCredit =
                tvRepo.getTVCreditData(tvId, AppConstants.API_KEY, AppConstants.LANGUAGE)
            try {
                tv_credit_data.postValue(getTVCredit.cast)
            } catch (e: Exception) {
                error
            }
        }
        return tv_credit_data
    }
    fun getSimilar(id : Int) : Flow<PagingData<OtherContent>> { return detailRepo.getSimilar(id).cachedIn(viewModelScope)}
    fun getRecommend(Id : Int) : Flow<PagingData<OtherContent>> { return detailRepo.getRecommend(Id).cachedIn(viewModelScope)}

    fun getMovieTrailer(Id : Int) : Flow<PagingData<Trailer>> { return movieRepo.getMovieTrailer(Id).cachedIn(viewModelScope)}
    fun getTVTrailer(Id : Int) : Flow<PagingData<Trailer>> { return tvRepo.getTVTrailer(Id).cachedIn(viewModelScope)}


    fun insert(bookmark: Bookmark) {

        viewModelScope.launch {
            bookmarkRepo.insert(bookmark)
        }
    }

    fun delete(bookmark: Bookmark){

        viewModelScope.launch {
            bookmarkRepo.delete(bookmark)
        }

    }

    suspend fun check(id: String) = bookmarkRepo.check(id)

    fun deleteAll(){
        viewModelScope.launch {
            bookmarkRepo.deleteAll()
        }
    }

    val bookmarks = bookmarkRepo.getBookmark()



}