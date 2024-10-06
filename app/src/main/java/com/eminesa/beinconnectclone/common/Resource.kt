package com.eminesa.beinconnectclone.common

sealed interface Resource<out T> {

    data class Success<out T>(val data: T) : Resource<T>

    data class Error(val message: String) : Resource<Nothing>

}


suspend fun <T : Any, N : Any> Resource<T>.onSuccess(data: suspend (T) -> N): Resource<N> {
    return when (this) {
        is Resource.Success -> {
            Resource.Success(data(this.data))
        }

        is Resource.Error -> Resource.Error(this.message)
    }
}

suspend fun <T : Any> Resource<T>.onSuccessForUI(data: suspend (T) -> Unit): Resource<T> {
    when (this) {
        is Resource.Success -> data(this.data)
        is Resource.Error -> this.message
    }
    return this
}

suspend fun <T : Any> Resource<T>.onFailure(failure: suspend (String) -> Unit): Resource<T> {
    when (this) {
        is Resource.Success -> this.data
        is Resource.Error -> failure(this.message)
    }
    return this
}