package com.eminesa.beinconnectclone.data.mapper

import com.eminesa.beinconnectclone.domain.model.GenreItem
import com.eminesa.beinconnectclone.domain.model.MovieItem
import com.eminesa.beinconnectclone.domain.model.MultiViewItem
import javax.inject.Inject

class GenreAndMovieUiMapper @Inject constructor(
    private val movieUiMapper: MovieUiMapper
) {
    fun map(genre: GenreItem, movies: List<MovieItem>): List<MultiViewItem> {
        val multiViewItemList = mutableListOf<MultiViewItem>()

        multiViewItemList.add(genre)
        multiViewItemList.add(movieUiMapper.map(movies))

        return multiViewItemList
    }

}