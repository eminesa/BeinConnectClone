package com.eminesa.beinconnectclone.domain.repository

import com.eminesa.beinconnectclone.common.Resource
import com.eminesa.beinconnectclone.domain.model.MovieItem

interface MovieRepository {

    suspend fun getMovie(genre: Int): Resource<List<MovieItem>>

}