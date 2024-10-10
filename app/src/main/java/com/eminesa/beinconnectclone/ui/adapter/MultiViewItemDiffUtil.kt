package com.eminesa.beinconnectclone.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.eminesa.beinconnectclone.domain.model.GenreItem
import com.eminesa.beinconnectclone.domain.model.MovieItem
import com.eminesa.beinconnectclone.domain.model.MultiViewItem

object MultiViewItemDiffUtil : DiffUtil.ItemCallback<MultiViewItem>() {

    override fun areItemsTheSame(oldItem: MultiViewItem, newItem: MultiViewItem): Boolean {
        return if (oldItem is MovieItem && newItem is MovieItem) {
            oldItem.id == newItem.id
        } else if (oldItem is GenreItem && newItem is GenreItem) {
            oldItem == newItem
        } else false
    }

    override fun areContentsTheSame(oldItem: MultiViewItem, newItem: MultiViewItem): Boolean {
        return if (oldItem is MovieItem && newItem is MovieItem) {
            oldItem == newItem
        } else if (oldItem is GenreItem && newItem is GenreItem) {
            oldItem == newItem
        } else false
    }
}