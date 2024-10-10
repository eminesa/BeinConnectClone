package com.eminesa.beinconnectclone.data.mapper

import com.eminesa.beinconnectclone.common.orFalse
import com.eminesa.beinconnectclone.common.orZero
import com.eminesa.beinconnectclone.data.dto.GenreItemResponse
import com.eminesa.beinconnectclone.data.dto.MovieItem
import com.eminesa.beinconnectclone.domain.model.GenreItem

fun List<MovieItem>?.toMovie(): List<com.eminesa.beinconnectclone.domain.model.MovieItem> {
    return this?.map { movie ->
        with(movie) {
            com.eminesa.beinconnectclone.domain.model.MovieItem(
                adult = adult.orFalse(),
                backdropPath = backdropPath.orEmpty(),
                genreIds = genreIds?.map { it.orZero() }.orEmpty(),
                id = id.orZero(),
                originalLanguage = originalLanguage.orEmpty(),
                originalTitle = originalTitle.orEmpty(),
                overview = overview.orEmpty(),
                popularity = popularity.orZero(),
                posterPath = posterPath.orEmpty(),
                releaseDate = releaseDate.orEmpty(),
                title = title.orEmpty(),
                video = video.orFalse(),
                voteAverage = voteAverage.orZero(),
                voteCount = voteCount.orZero(),
            )
        }
    } ?: emptyList()
}

fun List<GenreItemResponse>?.toGenre(): List<GenreItem> {
    return this?.map { genre ->
        with(genre) {
            GenreItem(
                id = id.orZero(),
                name = name.orEmpty()
            )
        }
    } ?: emptyList()
}