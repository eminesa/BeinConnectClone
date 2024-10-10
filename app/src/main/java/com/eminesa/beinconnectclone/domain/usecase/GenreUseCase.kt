package com.eminesa.beinconnectclone.domain.usecase

import coil.network.HttpException
import com.eminesa.beinconnectclone.common.Resource
import com.eminesa.beinconnectclone.common.onFailure
import com.eminesa.beinconnectclone.common.onSuccess
import com.eminesa.beinconnectclone.common.orZero
import com.eminesa.beinconnectclone.data.mapper.GenreAndMovieUiMapper
import com.eminesa.beinconnectclone.domain.model.MultiViewItem
import com.eminesa.beinconnectclone.domain.repository.GenreRepository
import com.eminesa.beinconnectclone.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GenreUseCase @Inject constructor(
    private val genreRepository: GenreRepository,
    private val movieRepository: MovieRepository,
    private val genreAndMovieUiMapper: GenreAndMovieUiMapper
) {
    operator fun invoke(): Flow<Resource<List<MultiViewItem>>> = flow {
        try {
            val mapperList: MutableList<MultiViewItem> = ArrayList()
            val genres = genreRepository.getGenre()

            genres.onSuccess { genreList ->
                if (genreList.isNotEmpty()) {
                    genreList.forEach { genre ->

                        val response = movieRepository.getMovie(genre.id.orZero())
                        response.onSuccess { movieList ->

                            val mappedMovies = genreAndMovieUiMapper.map(genre, movieList)
                            mapperList.addAll(mappedMovies)

                        }.onFailure { error ->
                            emit(Resource.Error(message = error))
                        }
                    }
                }

            }.onFailure { error ->
                emit(Resource.Error(error))

            }
            emit(Resource.Success(mapperList.toList()))


        } catch (e: HttpException) {
            emit(Resource.Error(e.message.orEmpty()))
        } catch (e: IOException) {
            emit(Resource.Error(e.message.orEmpty()))
        }
    }
}
