package com.eminesa.beinconnectclone.data.mapper

import com.eminesa.beinconnectclone.domain.model.MovieItem
import com.eminesa.beinconnectclone.domain.model.MovieListItem
import com.eminesa.beinconnectclone.domain.model.MultiViewItem
import javax.inject.Inject

class MovieUiMapper @Inject constructor() {

    fun map(movieList: List<MovieItem>): MultiViewItem {
        return MovieListItem(movies = movieList)
    }
}

