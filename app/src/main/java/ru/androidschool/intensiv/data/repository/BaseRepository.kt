package ru.androidschool.intensiv.data.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.data.network.util.CustomResult

abstract class BaseRepository {

    fun <T, R> handleResponse(
        call: Call<T>,
        mapper: (T) -> R,
        callback: (CustomResult<R>) -> Unit
    ) {
        callback(CustomResult.Loading)

        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val result = response.body()?.let { mapper(it) }
                    if (result != null) {
                        callback(CustomResult.Success(result))
                    } else {
                        callback(CustomResult.Error(Exception("Empty body")))
                    }
                } else {
                    callback(CustomResult.Error(Exception("error: ${response.code()}")))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback(CustomResult.Error(t))
            }
        })
    }
}
