package com.eminesa.beinconnectclone.ui.foreign

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.eminesa.beinconnectclone.R
import com.eminesa.beinconnectclone.common.createPosterUrl
import com.eminesa.beinconnectclone.databinding.ItemViewMovieBinding
import com.eminesa.beinconnectclone.domain.model.ResultItem


class MovieListAdapter(
    private val onItemClicked: (ResultItem) -> Unit
) : ListAdapter<ResultItem, MovieListAdapter.MovieViewHolder>(ResultItemDiffCallback()) {

    inner class MovieViewHolder(private val binding: ItemViewMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(resultItem: ResultItem) {
            val poster = resultItem.posterPath.createPosterUrl()

            binding.apply {
                imgPoster.load(poster)
                txtTitle.text = resultItem.title
                root.setOnClickListener {
                    onItemClicked(resultItem)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemViewMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val resultItem = getItem(position)
        holder.bind(resultItem)
    }
}

class ResultItemDiffCallback : DiffUtil.ItemCallback<ResultItem>() {
    override fun areItemsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
        return oldItem == newItem
    }
}
