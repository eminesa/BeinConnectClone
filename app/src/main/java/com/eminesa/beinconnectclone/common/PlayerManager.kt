package com.eminesa.beinconnectclone.common

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource

class PlayerManager(private val context: Context) {
    private var exoPlayer: ExoPlayer? = null
    private var playbackPosition = 0L
    private var playWhenReady = true

    var progressVisibleListener: ((isProgressVisible: Boolean) -> Unit)? = null

    @OptIn(UnstableApi::class)
    fun preparePlayer(mediaUrl: String) {

        exoPlayer = ExoPlayer.Builder(context)
            .setSeekBackIncrementMs(10000)
            .setSeekForwardIncrementMs(10000)
            .build()

        playerListener()

        val mediaItem = MediaItem.fromUri(mediaUrl)
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaSource =
            HlsMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)

        exoPlayer?.apply {
            setMediaSource(mediaSource)
            seekTo(playbackPosition)
            playWhenReady = playWhenReady
            prepare()
            play()
        }

    }

    fun getPlaybackPosition(): Long {
        return playbackPosition
    }

    private fun playerListener() {
        exoPlayer?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> {
                        progressVisibleListener?.invoke(true)
                    }

                    Player.STATE_READY -> {
                        progressVisibleListener?.invoke(false)
                    }

                    Player.STATE_ENDED -> {
                        progressVisibleListener?.invoke(false)
                    }

                    Player.STATE_IDLE -> {
                        progressVisibleListener?.invoke(false)
                    }
                }
            }
        })
    }

    fun releasePlayer() {
        exoPlayer?.let { player ->
            playbackPosition = player.currentPosition
            playWhenReady = player.playWhenReady
            player.release()
            exoPlayer = null
        }
    }

    fun play() {
        exoPlayer?.play()
    }

    fun pause() {
        exoPlayer?.pause()
    }

    fun isPlaying(): Boolean {
        return exoPlayer?.isPlaying == true
    }

    fun getPlayer(): ExoPlayer? {
        return exoPlayer
    }
}
