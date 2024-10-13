package com.eminesa.beinconnectclone.ui.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eminesa.beinconnectclone.common.PlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    val playerManager: PlayerManager
) : ViewModel() {

    // Oynatma pozisyonu
    private val _playbackPosition = MutableStateFlow(0L)
    val playbackPosition: StateFlow<Long> get() = _playbackPosition

    // Oynatma durumu (true = oynat, false = duraklat)
    private val _isPlaying = MutableStateFlow(true)
    val isPlaying: StateFlow<Boolean> get() = _isPlaying

    init {
        // PlayerManager'daki pozisyonu alarak güncelle
        _playbackPosition.value = playerManager.getPlayer()?.currentPosition ?: 0L
        _isPlaying.value = playerManager.isPlaying()
    }

    // Oynatma pozisyonunu güncelleme
    fun updatePlaybackPosition(position: Long) {
        viewModelScope.launch {
            _playbackPosition.emit(position)
            playerManager.getPlayer()?.seekTo(position)
        }
    }

    // Oynatma durumunu güncelleme
    fun togglePlayPause() {
        viewModelScope.launch {
            val isCurrentlyPlaying = !_isPlaying.value
            _isPlaying.emit(isCurrentlyPlaying)

            if (isCurrentlyPlaying) {
                playerManager.play()
            } else {
                playerManager.pause()
            }
        }
    }

    // ViewModel'den player'ı serbest bırakmak
    override fun onCleared() {
        super.onCleared()
        playerManager.releasePlayer()
    }
}
