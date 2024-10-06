package com.eminesa.beinconnectclone.common

fun Boolean?.orFalse()  = this ?: false

fun Int?.orZero() = this ?: 0

fun Double?.orZero() = this ?: 0.0