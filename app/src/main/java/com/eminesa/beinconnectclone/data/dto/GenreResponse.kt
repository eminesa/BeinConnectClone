package com.eminesa.beinconnectclone.data.dto


import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<GenreItem>? = null
)