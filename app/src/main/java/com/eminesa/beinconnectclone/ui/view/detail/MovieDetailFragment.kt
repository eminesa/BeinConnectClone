package com.eminesa.beinconnectclone.ui.view.detail

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
        initUI()
        observeViewModel()
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
                    playerView.player?.play()
                } else {
                    playerView.player?.pause()
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

    override fun onStop() {
        super.onStop()
        viewModel.playerManager.releasePlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.playerManager.releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        viewModel.playerManager.getPlaybackPosition().let { viewModel.updatePlaybackPosition(it) }
        viewModel.playerManager.releasePlayer()
    }
}
