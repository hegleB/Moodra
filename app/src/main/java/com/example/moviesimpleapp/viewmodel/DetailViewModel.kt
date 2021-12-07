package com.example.moviesimpleapp.viewmodel


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesimpleapp.AppConstants
import com.example.moviesimpleapp.repository.Repository
import com.example.moviesimpleapp.retrofit.*
import com.example.moviesimpleapp.room.Bookmark
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class DetailViewModel @ViewModelInject constructor(
    private val repository: Repository,

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
                repository.getMovieDetailData(movieId, AppConstants.language, AppConstants.api_key,)
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
                repository.getTVDetailData(tvId, AppConstants.language, AppConstants.api_key,)
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

//
//    fun getMovieTrailerData(movieId: Integer): MutableLiveData<List<Trailer>> {
//        scope.launch(Dispatchers.IO) {
//            val getMovieTrailerData =
//                repository.getMovieTrailerData(movieId, AppConstants.api_key, AppConstants.language)
//            try {
//                movie_trailer_data.postValue(getMovieTrailerData.results)
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    error
//                }
//            }
//
//        }
//
//        return movie_trailer_data
//
//    }

//    fun getTVTrailerData(tvId: Integer): MutableLiveData<List<TVTrailer>> {
//        scope.launch(Dispatchers.IO) {
//            val getTVTrailerData =
//                repository.getTVTrailerData(tvId, AppConstants.api_key, AppConstants.language)
//            try {
//                tv_trailer_data.postValue(getTVTrailerData.tvTrailerList)
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    error
//                }
//            }
//        }
//
//        return tv_trailer_data
//    }

    fun getMovieCredit(movieId: Integer): MutableLiveData<List<Actor>> {

        scope.launch(Dispatchers.IO) {
            val getMovieCredit =
                repository.getMovieCreditData(movieId, AppConstants.api_key, AppConstants.language)
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
                repository.getTVCreditData(tvId, AppConstants.api_key, AppConstants.language)
            try {
                tv_credit_data.postValue(getTVCredit.cast)
            } catch (e: Exception) {
                error
            }
        }
        return tv_credit_data
    }
    fun getSimilar(id : Int) : Flow<PagingData<OtherContent>> { return repository.getSimilar(id).cachedIn(viewModelScope)}
    fun getRecommend(Id : Int) : Flow<PagingData<OtherContent>> { return repository.getRecommend(Id).cachedIn(viewModelScope)}
    fun getTrailer(Id : Int) : Flow<PagingData<Trailer>> { return repository.getTrailer(Id).cachedIn(viewModelScope)}


    fun insert(bookmark: Bookmark) {

        viewModelScope.launch {
            repository.insert(bookmark)
        }
    }

    fun delete(bookmark: Bookmark){

        viewModelScope.launch {
            repository.delete(bookmark)
        }

    }

    suspend fun check(id: String) = repository.check(id)

    fun deleteAll(){
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    val bookmarks = repository.getBookmark()



}