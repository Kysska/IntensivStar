package ru.androidschool.intensiv.data.network.util

sealed class CustomResult<out T> {
    data object Loading : CustomResult<Nothing>()
    data class Success<T>(val data: T) : CustomResult<T>()
    data class Error(val error: Throwable) : CustomResult<Nothing>()
}
