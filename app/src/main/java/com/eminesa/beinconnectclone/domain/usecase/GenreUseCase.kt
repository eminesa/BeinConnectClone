package com.eminesa.beinconnectclone.domain.usecase

import com.eminesa.beinconnectclone.common.Resource
import com.eminesa.beinconnectclone.domain.model.GenreItem
import com.eminesa.beinconnectclone.domain.repository.GenreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenreUseCase @Inject constructor(
    private val movieRepository: GenreRepository
) {
    operator fun invoke(): Flow<Resource<List<GenreItem>>> = flow {
        emit(movieRepository.getGenre())
    }
}