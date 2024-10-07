package com.eminesa.beinconnectclone.common

import android.view.View

fun Boolean?.orFalse() = this ?: false

fun Int?.orZero() = this ?: 0

fun Double?.orZero() = this ?: 0.0

fun String.createPosterUrl(): String {
    val baseUrl = "https://image.tmdb.org/t/p/w185/"
    return baseUrl + this
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
