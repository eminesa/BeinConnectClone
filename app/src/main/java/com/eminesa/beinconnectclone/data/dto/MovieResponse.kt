package com.eminesa.beinconnectclone.data.dto


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<MovieItem>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
)