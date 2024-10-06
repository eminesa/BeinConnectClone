package com.eminesa.beinconnectclone.domain.model


data class ResultItem(
    val adult: Boolean = false,
    val backdropPath: String = "",
    val genreIds: List<Int> = listOf(),
    val id: Int = 1,
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val releaseDate: String = "",
    val title: String = "",
    val video: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0
)