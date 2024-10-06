package com.eminesa.beinconnectclone.domain.usecase

import com.eminesa.beinconnectclone.common.Resource
import com.eminesa.beinconnectclone.domain.model.ResultItem
import com.eminesa.beinconnectclone.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(genre: Int): Flow<Resource<List<ResultItem>>>  = flow{ // veri üzerinde bir işlem yapmak istiyorsan(collect) burada flow ile emit üzerinde gideceksin
        emit(movieRepository.getMovie(genre))
    }
}