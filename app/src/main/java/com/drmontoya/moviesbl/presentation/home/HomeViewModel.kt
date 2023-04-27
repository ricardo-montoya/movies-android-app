package com.drmontoya.moviesbl.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drmontoya.moviesbl.domain.Resource
import com.drmontoya.moviesbl.domain.model.Movie
import com.drmontoya.moviesbl.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: MovieRepository
) : ViewModel() {
    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: LiveData<List<Movie>>
        get() = _topRatedMovies
    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies: LiveData<List<Movie>>
        get() = _nowPlayingMovies

    private val _errorOccurred = MutableLiveData<String?>()
    val errorOccurred: LiveData<String?>
        get() = _errorOccurred

    private val _isTopRatedLoading = MutableLiveData<Boolean>()
    val isTopRatedLoading: LiveData<Boolean>
        get() = _isTopRatedLoading

    private val _isNowPlayingLoading = MutableLiveData<Boolean>()
    val isNowPlayingLoading: LiveData<Boolean>
        get() = _isNowPlayingLoading

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.upsertTopRatedMovies()
                repository.upsertNowPlayingMovies()
            }
        }
        getTopRatedMovies()
        getNowPlayingMovies()
    }


    fun getNowPlayingMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getPlayingNowMovies().onEach {
                    when (it) {
                        is Resource.Failed -> {
                            _isNowPlayingLoading.postValue(false)
                            _errorOccurred.postValue(it.message)
                        }

                        Resource.Loading -> {
                            _isNowPlayingLoading.postValue(true)
                        }

                        is Resource.Success -> {
                            _isNowPlayingLoading.postValue(false)
                            _nowPlayingMovies.postValue(it.data)
                        }
                    }
                }.collect()
            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getTopRatedMovies().onEach {
                    when (it) {
                        is Resource.Failed -> {
                            _isTopRatedLoading.postValue(false)
                            _errorOccurred.postValue(it.message)
                        }

                        Resource.Loading -> {
                            _isTopRatedLoading.postValue(true)
                        }

                        is Resource.Success -> {
                            _isTopRatedLoading.postValue(false)
                            _topRatedMovies.postValue(it.data)
                        }
                    }
                }.collect()
            }
        }
    }

    fun filterListsByKeyword(keyword: String) {
        if (keyword.isBlank() || keyword.isEmpty()) {
            getTopRatedMovies()
            getNowPlayingMovies()
        } else {
            _nowPlayingMovies.postValue(
                nowPlayingMovies.value?.filter {
                    it.original_title.contains(keyword)
                            || it.overview.contains(keyword)
                }
            )
            _topRatedMovies.postValue(
                topRatedMovies.value?.filter {
                    it.original_title.contains(keyword)
                            || it.overview.contains(keyword)
                }
            )
        }
    }

    fun erroMessageHasDisplayed() {
        _errorOccurred.postValue(null)
    }
}