package com.eminesa.beinconnectclone.domain.repository

import com.eminesa.beinconnectclone.common.Resource
import com.eminesa.beinconnectclone.data.dto.MovieResponse
import com.eminesa.beinconnectclone.domain.model.ResultItem
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovie(): Resource<List<ResultItem>>

}