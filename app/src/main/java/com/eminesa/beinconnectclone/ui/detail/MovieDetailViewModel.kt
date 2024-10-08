package com.eminesa.beinconnectclone.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {

    // Oynatma pozisyonu
    private val _playbackPosition = MutableStateFlow(0L)
    val playbackPosition: StateFlow<Long> get() = _playbackPosition

    // Oynatma durumu (true = oynat, false = duraklat)
    private val _isPlaying = MutableStateFlow(true)
    val isPlaying: StateFlow<Boolean> get() = _isPlaying

    // Oynatma pozisyonunu güncelleme
    fun updatePlaybackPosition(position: Long) {
        viewModelScope.launch {
            _playbackPosition.emit(position)
        }
    }

    // Oynatma durumunu güncelleme
    fun togglePlayPause() {
        viewModelScope.launch {
            _isPlaying.emit(!_isPlaying.value)
        }
    }
}
