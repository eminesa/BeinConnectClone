package com.eminesa.beinconnectclone.ui.detail

import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.eminesa.beinconnectclone.R
import com.eminesa.beinconnectclone.common.PlayerManager
import com.eminesa.beinconnectclone.common.gone
import com.eminesa.beinconnectclone.common.visible
import com.eminesa.beinconnectclone.databinding.FragmentMovieDetailBinding
import com.eminesa.beinconnectclone.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

    private lateinit var exoPlayerManager: PlayerManager

    override fun FragmentMovieDetailBinding.bindScreen() {
        initBinding()
        initListener()
    }

    private fun FragmentMovieDetailBinding.initListener() {
        exoPlayerManager.progressVisibleListener = { isProgressVisible ->
            progressBar.isVisible = isProgressVisible
        }

        //exoplayer components listener
        val headerTextView = playerView.findViewById<AppCompatTextView>(R.id.header_tv)
        val closeImg = playerView.findViewById<AppCompatImageView>(R.id.cross_im)
        val exoPauseBtn = playerView.findViewById<ImageButton>(R.id.exo_pause)
        val exoPlayBtn = playerView.findViewById<ImageButton>(R.id.exo_play)

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

    private fun FragmentMovieDetailBinding.initBinding() {

        exoPlayerManager = PlayerManager(requireContext())
        exoPlayerManager.apply {
            preparePlayer(getString(R.string.media_url))
            playerView.player = getPlayer()


        }
    }

    private fun controlPlay(exoPauseBtn: ImageButton, exoPlayBtn: ImageButton) {
        if (exoPlayerManager.isPlaying()) {
            exoPlayBtn.visible()
            exoPauseBtn.gone()
            exoPlayerManager.pause()
        } else {
            exoPlayBtn.gone()
            exoPauseBtn.visible()
            exoPlayerManager.play()
        }
    }

    private fun releasePlayer() {
        exoPlayerManager.releasePlayer()
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
