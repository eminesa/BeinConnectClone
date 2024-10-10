package com.eminesa.beinconnectclone.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eminesa.beinconnectclone.databinding.ItemViewGenreBinding
import com.eminesa.beinconnectclone.databinding.ItemViewMuviBinding
import com.eminesa.beinconnectclone.domain.model.GenreItem
import com.eminesa.beinconnectclone.domain.model.MovieItem
import com.eminesa.beinconnectclone.domain.model.MovieListItem
import com.eminesa.beinconnectclone.domain.model.MultiViewItem
import com.eminesa.beinconnectclone.ui.enums.MovieType

class MovieAdapter(
    private val onItemClicked: (MovieItem) -> Unit
) : ListAdapter<MultiViewItem, RecyclerView.ViewHolder>(MultiViewItemDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MovieType.TITLE.ordinal -> {
                GenreViewHolder(
                    binding = ItemViewGenreBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }

            MovieType.MOVIE.ordinal -> {
                MovieViewHolder(
                    binding = ItemViewMuviBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }

            else -> throw IllegalArgumentException("Invalid type of view type $viewType")
        }
    }


    override fun getItemViewType(position: Int): Int {

        return when (getItem(position)) {
            is MovieListItem -> MovieType.MOVIE.ordinal
            is GenreItem -> MovieType.TITLE.ordinal
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is MovieViewHolder && item is MovieListItem) {
            holder.bind(item)

        } else if (holder is GenreViewHolder && item is GenreItem) {
            holder.bind(item)
        }
    }

    inner class GenreViewHolder(private val binding: ItemViewGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genreItem: GenreItem) {
            binding.apply {
                txtGenreTitle.text = genreItem.name
            }
        }
    }

    inner class MovieViewHolder(private val binding: ItemViewMuviBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(resultItem: MovieListItem) {
            val movieAdapter = MovieListAdapter(onItemClicked)
            movieAdapter.submitList(resultItem.movies)
            binding.apply {
                recyclerView.adapter = movieAdapter
            }
        }
    }


}
