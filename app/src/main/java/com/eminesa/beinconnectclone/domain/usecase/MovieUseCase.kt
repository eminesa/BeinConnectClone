package com.eminesa.beinconnectclone.domain.usecase

import coil.network.HttpException
import com.eminesa.beinconnectclone.common.Resource
import com.eminesa.beinconnectclone.domain.model.MovieItem
import com.eminesa.beinconnectclone.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(genre: Int): Flow<Resource<List<MovieItem>>>  = flow{
        try {
            emit(movieRepository.getMovie(genre))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message.orEmpty()))
        } catch (e: IOException) {
            emit(Resource.Error(e.message.orEmpty()))
        }
    }
}