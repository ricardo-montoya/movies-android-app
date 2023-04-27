package com.drmontoya.moviesbl.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drmontoya.moviesbl.domain.model.Movie
import com.drmontoya.moviesbl.domain.use_case.GetMovieByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject constructor(
    val getMovieByIdUseCase: GetMovieByIdUseCase
) : ViewModel() {
    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val _errorOccurred = MutableLiveData<String?>()
    val errorOccurred: LiveData<String?>
        get() = _errorOccurred

    fun loadMovieWithId(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val movie = getMovieByIdUseCase(id)
                if (movie != null) {
                    _movie.postValue(movie!!)
                } else {
                    _errorOccurred.postValue("Verifica tu conexión a internet")
                }
            }
        }
    }

    fun snackbarHasDisplayed(){
        _errorOccurred.postValue(null)
    }
}