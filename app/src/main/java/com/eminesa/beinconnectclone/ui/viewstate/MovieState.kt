package com.eminesa.beinconnectclone.ui.viewstate

import com.eminesa.beinconnectclone.domain.model.MultiViewItem

data class MovieState(
    val isLoading: Boolean = false,
    val movieData: List<MultiViewItem> = emptyList() // Başlangıçta boş bir liste
)
