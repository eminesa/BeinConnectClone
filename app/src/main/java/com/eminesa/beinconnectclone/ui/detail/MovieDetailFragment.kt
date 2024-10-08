package com.eminesa.beinconnectclone.ui.detail

import android.widget.ImageButton
import androidx.annotation.OptIn
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.navigation.fragment.findNavController
import com.eminesa.beinconnectclone.R
import com.eminesa.beinconnectclone.common.gone
import com.eminesa.beinconnectclone.common.visible
import com.eminesa.beinconnectclone.databinding.FragmentMovieDetailBinding
import com.eminesa.beinconnectclone.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

    private var exoPlayer: ExoPlayer? = null
    private var playbackPosition = 0L
    private var playWhenReady = true

    override fun FragmentMovieDetailBinding.bindScreen() {

        preparePlayer()

        val headerTextView = videoView.findViewById<AppCompatTextView>(R.id.header_tv)
        val closeImg = videoView.findViewById<AppCompatImageView>(R.id.cross_im)
        val exoPauseBtn = videoView.findViewById<ImageButton>(R.id.exo_pause)
        val exoPlayBtn = videoView.findViewById<ImageButton>(R.id.exo_play)

        headerTextView.text = "New Dynamic Text"

        closeImg.setOnClickListener {
            releasePlayer()
            findNavController().popBackStack()
        }

        exoPlayBtn.setOnClickListener {
            controlPlay(exoPauseBtn, exoPlayBtn)
        }

        exoPauseBtn.setOnClickListener {
            controlPlay(exoPauseBtn, exoPlayBtn)
        }

    }

    private fun controlPlay(exoPauseBtn: ImageButton, exoPlayBtn: ImageButton) {
        if (exoPlayer?.isPlaying == true) {
            exoPlayBtn.visible()
            exoPauseBtn.gone()
            exoPlayer?.pause()
        } else {
            exoPlayBtn.gone()
            exoPauseBtn.visible()
            exoPlayer?.play()
        }
    }

    @OptIn(UnstableApi::class)
    private fun FragmentMovieDetailBinding.preparePlayer() {
        exoPlayer = ExoPlayer.Builder(requireContext()).setSeekBackIncrementMs(10000)
            .setSeekForwardIncrementMs(10000).build()
        exoPlayer?.playWhenReady = true
        videoView.player = exoPlayer
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaItem =
            MediaItem.fromUri(getString(R.string.media_url))
        val mediaSource =
            HlsMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)
        exoPlayer?.setMediaSource(mediaSource)
        exoPlayer?.seekTo(playbackPosition)
        exoPlayer?.playWhenReady = playWhenReady
        exoPlayer?.prepare()

    }

    private fun releasePlayer() {
        exoPlayer?.let { player ->
            playbackPosition = player.currentPosition
            playWhenReady = player.playWhenReady
            player.release()
            exoPlayer = null
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        releasePlayer()
    }


}