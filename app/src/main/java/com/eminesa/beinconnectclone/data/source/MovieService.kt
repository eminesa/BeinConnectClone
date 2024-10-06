package com.eminesa.beinconnectclone.data.source

import com.eminesa.beinconnectclone.data.dto.GenreResponse
import com.eminesa.beinconnectclone.data.dto.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenreResponse>

    @GET("discover/movie")
    suspend fun getMovie(
         @Query("with_genres") genreId: Int
    ): Response<MovieResponse>

}