package ru.androidschool.intensiv.utils.extensions

const val RATING_DIVISOR = 2.0

fun Double.toRating(): Float = this.div(RATING_DIVISOR).toFloat()
