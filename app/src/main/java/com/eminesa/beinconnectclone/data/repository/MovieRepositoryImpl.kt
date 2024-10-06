package com.eminesa.beinconnectclone.data.repository

import com.eminesa.beinconnectclone.common.Resource
import com.eminesa.beinconnectclone.common.onSuccess
import com.eminesa.beinconnectclone.data.dto.MovieItem
import com.eminesa.beinconnectclone.data.dto.MovieResponse
import com.eminesa.beinconnectclone.data.mapper.toMovie
import com.eminesa.beinconnectclone.data.source.MovieService
import com.eminesa.beinconnectclone.domain.model.ResultItem
import com.eminesa.beinconnectclone.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieService
) : MovieRepository, BaseRepository() {
    override suspend fun getMovie(): Resource<List<ResultItem>> = safeApiCall {
        api.getMovie(genreId = 28)
    }.onSuccess {
        it.results.toMovie()
    }
}