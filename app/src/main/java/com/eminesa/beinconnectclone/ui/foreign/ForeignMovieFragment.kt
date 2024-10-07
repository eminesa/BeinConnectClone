package com.eminesa.beinconnectclone.ui.foreign

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eminesa.beinconnectclone.R
import com.eminesa.beinconnectclone.databinding.FragmentForeignMovieBinding
import com.eminesa.beinconnectclone.ui.base.BaseFragment
import com.eminesa.beinconnectclone.ui.home.GenreState
import com.eminesa.beinconnectclone.ui.home.HomeViewModel
import com.eminesa.beinconnectclone.ui.home.MovieState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForeignMovieFragment :
    BaseFragment<FragmentForeignMovieBinding>(FragmentForeignMovieBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private var adapter: MovieListAdapter? = null

    override fun FragmentForeignMovieBinding.bindScreen() {

        viewModel.getMovie(28)

        recyclerView()

        lifecycleScope.launch {
            viewModel.movieState.collect { movieState ->
                updateMovieUI(movieState)
            }
        }

        /* viewModel.getGenre()
         lifecycleScope.launch {
             viewModel.genreState.collect { genreState ->
                 updateUI(genreState)
             }
         } */
    }

    private fun FragmentForeignMovieBinding.recyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        adapter = MovieListAdapter(onItemClicked = {
            findNavController().navigate(R.id.action_homeFragment_to_movieDetailFragment)
        })
        recyclerView.adapter = adapter
    }

    private fun updateUI(genreState: GenreState) {
        // UI güncellemeleri
        //binding.progressBar.visibility = if (genreState.isLoading) View.VISIBLE else View.GONE

        if (genreState.genreData.isNotEmpty()) {
            viewModel.getMovie(28)
        } else {

        }
    }

    private fun updateMovieUI(movieState: MovieState) {
        // UI güncellemeleri
        //binding.progressBar.visibility = if (genreState.isLoading) View.VISIBLE else View.GONE

        if (movieState.movieData.isNotEmpty()) {
            adapter?.submitList(movieState.movieData)
        } else {

        }
    }

}