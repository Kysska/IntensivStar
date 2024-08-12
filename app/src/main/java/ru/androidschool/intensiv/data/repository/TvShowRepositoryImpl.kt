package ru.androidschool.intensiv.data.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.data.network.util.CustomResult
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.dto.MovieListResponse
import ru.androidschool.intensiv.data.network.mapper.MovieMapper
import ru.androidschool.intensiv.domain.entity.MovieCard

class TvShowRepositoryImpl(private val movieApiInterface: MovieApiInterface) {

    fun getTvShows(callback: (CustomResult<List<MovieCard>>) -> Unit) {
        callback(CustomResult.Loading)

        movieApiInterface.getPopularTvShows().enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if (response.isSuccessful) {
                    val tvShows = response.body()?.results?.map { MovieMapper.mapToMovieCardEntity(it) } ?: emptyList()
                    callback(CustomResult.Success(tvShows))
                } else {
                    callback(CustomResult.Error(Exception("error: ${response.code()}")))
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                callback(CustomResult.Error(t))
            }
        })
    }
}
