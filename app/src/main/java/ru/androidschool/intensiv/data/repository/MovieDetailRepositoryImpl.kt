package ru.androidschool.intensiv.data.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.dto.MovieDetailResponse
import ru.androidschool.intensiv.data.network.mapper.MovieMapper
import ru.androidschool.intensiv.data.network.util.CustomResult
import ru.androidschool.intensiv.domain.entity.MovieDetail

class MovieDetailRepositoryImpl(private val movieApiInterface: MovieApiInterface) {

    fun getMovieDetail(id: Int, callback: (CustomResult<MovieDetail>) -> Unit) {
        callback(CustomResult.Loading)

        movieApiInterface.getMovieDetailById(id).enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.let { MovieMapper.mapToMovieDetailEntity(it) } ?: MovieDetail()
                    callback(CustomResult.Success(movies))
                } else {
                    callback(CustomResult.Error(Exception("error: ${response.code()}")))
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                callback(CustomResult.Error(t))
            }
        })
    }
}
