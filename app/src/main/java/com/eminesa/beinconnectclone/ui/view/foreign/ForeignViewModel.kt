package com.eminesa.beinconnectclone.ui.view.foreign

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eminesa.beinconnectclone.common.onFailure
import com.eminesa.beinconnectclone.common.onSuccess
import com.eminesa.beinconnectclone.common.orZero
import com.eminesa.beinconnectclone.data.mapper.GenreAndMovieUiMapper
import com.eminesa.beinconnectclone.domain.model.GenreItem
import com.eminesa.beinconnectclone.domain.usecase.GenreUseCase
import com.eminesa.beinconnectclone.domain.usecase.MovieUseCase
import com.eminesa.beinconnectclone.ui.viewstate.MovieState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val genreUseCase: GenreUseCase,
    private val genreAndMovieUiMapper: GenreAndMovieUiMapper
) : ViewModel() {

    private val _movieState: MutableStateFlow<MovieState> = MutableStateFlow(MovieState())
    val movieState: StateFlow<MovieState> get() = _movieState.asStateFlow()

    fun getGenre() {
        if (movieState.value.movieData.isEmpty()) {
            genreUseCase()
                .onEach { result ->
                    result.onSuccess { genres ->
                        genres.forEach {
                            getMovie(it)
                        }
                    }.onFailure {
                        Log.e("GET_GENRE", result.toString())
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun getMovie(genre: GenreItem) {
        movieUseCase(genre.id.orZero())
            .onStart {
                _movieState.value = movieState.value.copy(isLoading = true)
            }
            .onCompletion {
                _movieState.value = movieState.value.copy(isLoading = false)
            }
            .onEach { result ->
                result.onSuccess { movieItems ->

                    val mappedMovies = genreAndMovieUiMapper.map(genre, movieItems)
                    val updatedMovieData = _movieState.value.movieData + mappedMovies

                    _movieState.value = movieState.value.copy(movieData = updatedMovieData)

                    val gson = Gson()
                    val jsonString = gson.toJson(genreAndMovieUiMapper)
                    Log.i(genre.name?.uppercase(), jsonString)

                }.onFailure {
                    Log.e("GET_MOVIE", result.toString())
                }
            }.launchIn(viewModelScope)
    }
}


