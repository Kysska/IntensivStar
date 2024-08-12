package ru.androidschool.intensiv.data.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.dto.CreditResponse
import ru.androidschool.intensiv.data.network.mapper.MovieMapper
import ru.androidschool.intensiv.data.network.util.CustomResult
import ru.androidschool.intensiv.domain.entity.CastCard

class CastRepositoryImpl(private val movieApiInterface: MovieApiInterface) {

    fun getCasts(id: Int, callback: (CustomResult<List<CastCard>>) -> Unit) {
        callback(CustomResult.Loading)

        movieApiInterface.getCastsListById(id).enqueue(object : Callback<CreditResponse> {
            override fun onResponse(call: Call<CreditResponse>, response: Response<CreditResponse>) {
                if (response.isSuccessful) {
                    val casts = response.body()?.castsList?.map { MovieMapper.mapToCastCardEntity(it) } ?: emptyList()
                    callback(CustomResult.Success(casts))
                } else {
                    callback(CustomResult.Error(Exception("error: ${response.code()}")))
                }
            }

            override fun onFailure(call: Call<CreditResponse>, t: Throwable) {
                callback(CustomResult.Error(t))
            }
        })
    }
}
