package com.eminesa.beinconnectclone.domain.repository

import com.eminesa.beinconnectclone.common.Resource
import com.eminesa.beinconnectclone.domain.model.GenreItem

interface GenreRepository {

    suspend fun getGenre(): Resource<List<GenreItem>>

}