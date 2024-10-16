package com.eminesa.beinconnectclone.data.dto


import com.google.gson.annotations.SerializedName

data class GenreItemResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null
)