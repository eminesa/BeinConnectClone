package com.eminesa.beinconnectclone.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.eminesa.beinconnectclone.domain.model.MovieItem

object ResultItemDiffCallback : DiffUtil.ItemCallback<MovieItem>() {
    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem == newItem
    }
}
