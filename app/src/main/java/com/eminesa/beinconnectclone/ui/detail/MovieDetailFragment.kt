package com.eminesa.beinconnectclone.ui.detail

import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.media3.common.Player
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
        exoPlayerManager = PlayerManager(requireContext())
        exoPlayerManager.preparePlayer(getString(R.string.media_url))

        playerView.player = exoPlayerManager.getPlayer()

        exoPlayerManager.getPlayer()?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> {
                        progressBar.visible()
                        // Video buffering, loading spinner gösterebilirsiniz.
                    }

                    Player.STATE_READY -> {
                        progressBar.gone()
                        // Player hazır, player view'i görünür yapın.
                    }

                    Player.STATE_ENDED -> {
                        progressBar.gone()
                        // Video sona erdi.
                    }

                    Player.STATE_IDLE -> {
                        progressBar.gone()
                        // Player idle durumda.
                    }
                }
            }
        })

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
