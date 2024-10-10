package com.eminesa.beinconnectclone.ui.view.foreign

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eminesa.beinconnectclone.common.onFailure
import com.eminesa.beinconnectclone.common.onSuccess
import com.eminesa.beinconnectclone.domain.usecase.GenreUseCase
import com.eminesa.beinconnectclone.ui.viewstate.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val genreUseCase: GenreUseCase,
) : ViewModel() {

    private val _movieState: MutableStateFlow<MovieState> = MutableStateFlow(MovieState())
    val movieState: StateFlow<MovieState> get() = _movieState.asStateFlow()

    fun getGenre() {
        if (movieState.value.movieData.isEmpty()) {
            genreUseCase()
                .onEach { result ->
                    result.onSuccess { movieWithGenre ->

                        _movieState.value = movieState.value.copy(movieData = movieWithGenre)

                    }.onFailure {
                        Log.e("GET_GENRE", result.toString())
                    }
                }.launchIn(viewModelScope)
        }
    }

}


