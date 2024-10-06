package com.eminesa.beinconnectclone.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eminesa.beinconnectclone.common.Resource
import com.eminesa.beinconnectclone.common.onFailure
import com.eminesa.beinconnectclone.common.onSuccess
import com.eminesa.beinconnectclone.data.dto.MovieItem
import com.eminesa.beinconnectclone.domain.model.ResultItem
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
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _homeState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState

    fun getMovie() { //responslu bir şey istiyorsan Flow döndür
        movieUseCase()
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
