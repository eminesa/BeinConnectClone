package com.eminesa.beinconnectclone.data.repository

import com.eminesa.beinconnectclone.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseRepository {
    suspend fun <T : Any> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {

        return withContext(Dispatchers.IO) {
            try {
                val response = apiToBeCalled.invoke()

                if (response.isSuccessful) {

                    response.body()?.let { data ->
                        Resource.Success(data)
                    } ?: Resource.Error("")

                } else {
                    Resource.Error("")
                }
            } catch (throwable: Throwable) {
                Resource.Error("")
            }
        }

    }
}