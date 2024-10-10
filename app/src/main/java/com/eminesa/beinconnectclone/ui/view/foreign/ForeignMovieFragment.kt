package com.eminesa.beinconnectclone.ui.view.foreign

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eminesa.beinconnectclone.R
import com.eminesa.beinconnectclone.databinding.FragmentForeignMovieBinding
import com.eminesa.beinconnectclone.ui.adapter.MovieAdapter
import com.eminesa.beinconnectclone.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForeignMovieFragment : BaseFragment<FragmentForeignMovieBinding>(
    FragmentForeignMovieBinding::inflate
) {

    private val viewModel: HomeViewModel by viewModels()
    private var movieAdapter: MovieAdapter? = null

    override fun FragmentForeignMovieBinding.bindScreen() {

        viewModel.getGenre()
        movieAdapter = movieAdapter()
        listenGenre()
    }

    private fun movieAdapter() = MovieAdapter(onItemClicked = {

        val movieTitle: String = it.title
        val bundle = Bundle().apply { putString("movieTitle", movieTitle) }

        findNavController().navigate(
            R.id.action_homeFragment_to_movieDetailFragment,
            bundle
        )

    })

    private fun FragmentForeignMovieBinding.listenGenre() {

        lifecycleScope.launch {
            viewModel.movieState.collect { movieState ->
                progressBar.isVisible = movieState.isLoading
                recyclerView.adapter = movieAdapter
                movieAdapter?.submitList(movieState.movieData)
            }
        }
    }

}