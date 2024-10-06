package com.eminesa.beinconnectclone.data.repository

import com.eminesa.beinconnectclone.common.Resource
import com.eminesa.beinconnectclone.common.onSuccess
import com.eminesa.beinconnectclone.data.mapper.toGenre
import com.eminesa.beinconnectclone.data.source.MovieService
import com.eminesa.beinconnectclone.domain.model.GenreItem
import com.eminesa.beinconnectclone.domain.repository.GenreRepository
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val api: MovieService
) : GenreRepository, BaseRepository() {
    override suspend fun getGenre(): Resource<List<GenreItem>> = safeApiCall {
        api.getGenres()
    }.onSuccess {
        it.genres.toGenre()
    }
}