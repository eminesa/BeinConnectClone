package com.eminesa.beinconnectclone.ui.view.detail

import android.content.pm.ActivityInfo
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eminesa.beinconnectclone.R
import com.eminesa.beinconnectclone.common.gone
import com.eminesa.beinconnectclone.common.visible
import com.eminesa.beinconnectclone.databinding.FragmentMovieDetailBinding
import com.eminesa.beinconnectclone.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun FragmentMovieDetailBinding.bindScreen() {
        observeViewModel()
        setFullScreen(true)
        initUI()
        initListeners()
    }

    private fun FragmentMovieDetailBinding.initUI() {

        viewModel.playerManager.preparePlayer(getString(R.string.media_url))
        playerView.player = viewModel.playerManager.getPlayer()

        val movieTitle = arguments?.getString("movieTitle")
        playerView.findViewById<AppCompatTextView>(R.id.header_tv).text = movieTitle
    }

    private fun FragmentMovieDetailBinding.observeViewModel() {

        lifecycleScope.launch {
            viewModel.playbackPosition.collect { position ->
                playerView.player?.seekTo(position)
            }
        }

        lifecycleScope.launch {
            viewModel.isPlaying.collect { isPlaying ->
                if (isPlaying) {
                    playerView.player?.pause()
                } else {
                    playerView.player?.play()
                }
            }
        }
    }

    private fun FragmentMovieDetailBinding.initListeners() {

        viewModel.playerManager.progressVisibleListener = { isProgressVisible ->
            progressBar.isVisible = isProgressVisible
        }

        val closeImg = playerView.findViewById<AppCompatImageView>(R.id.cross_im)
        val exoPauseBtn = playerView.findViewById<ImageButton>(R.id.exo_pause)
        val exoPlayBtn = playerView.findViewById<ImageButton>(R.id.exo_play)

        closeImg.setOnClickListener {
            setFullScreen(false)
            viewModel.playerManager.getPlaybackPosition().let { viewModel.updatePlaybackPosition(0) }
            viewModel.playerManager.releasePlayer()

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
        if (viewModel.playerManager.isPlaying()) {
            exoPlayBtn.visible()
            exoPauseBtn.gone()
            viewModel.togglePlayPause()
        } else {
            exoPlayBtn.gone()
            exoPauseBtn.visible()
            viewModel.togglePlayPause()
        }
    }

    private fun setFullScreen(enable: Boolean) {
        if (enable) {
            requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        } else {
            requireActivity().apply {
                // Portre modunu bırakma
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.playerManager.releasePlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.playerManager.releasePlayer()
        setFullScreen(false)
    }

    override fun onPause() {
        super.onPause()
        viewModel.playerManager.getPlaybackPosition().let { viewModel.updatePlaybackPosition(it) }
        viewModel.playerManager.releasePlayer()
    }
}
