package com.eminesa.beinconnectclone.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eminesa.beinconnectclone.common.onFailure
import com.eminesa.beinconnectclone.common.onSuccess
import com.eminesa.beinconnectclone.domain.model.GenreItem
import com.eminesa.beinconnectclone.domain.model.ResultItem
import com.eminesa.beinconnectclone.domain.usecase.GenreUseCase
import com.eminesa.beinconnectclone.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val genreUseCase: GenreUseCase
) : ViewModel() {

    private val _homeState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState

    private val _genreState: MutableStateFlow<GenreState> = MutableStateFlow(GenreState())
    val genreState: StateFlow<GenreState> = _genreState

    fun getGenre() { //responslu bir şey istiyorsan Flow döndür
        genreUseCase()
            .onStart {
                _genreState.value = genreState.value.copy(isLoading = true)
            }
            .onCompletion {
                _genreState.value = genreState.value.copy(isLoading = false)
            }
            .onEach { result ->

                result.onSuccess {
                    _genreState.value = genreState.value.copy(movieData = it)
                    println(result.toString())
                }.onFailure {
                    println(result.toString())
                }


            }.launchIn(viewModelScope)
    }

    fun getMovie(genre: Int) { //responslu bir şey istiyorsan Flow döndür
        movieUseCase(genre)
            .onStart {
                _homeState.value = homeState.value.copy(isLoading = true)
            }
            .onCompletion {
                _homeState.value = homeState.value.copy(isLoading = false)
            }
            .onEach { result ->

                result.onSuccess {
                    _homeState.value = homeState.value.copy(movieData = it)
                    println(result.toString())
                }.onFailure {
                    println(result.toString())
                }


            }.launchIn(viewModelScope)
    }
}

data class HomeState(
    val isLoading: Boolean = false,
    val movieData: List<ResultItem> = emptyList()
)

data class GenreState(
    val isLoading: Boolean = false,
    val movieData: List<GenreItem> = emptyList()
)
