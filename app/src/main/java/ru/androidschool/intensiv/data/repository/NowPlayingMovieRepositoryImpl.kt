package ru.androidschool.intensiv.data.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.data.network.util.CustomResult
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.dto.MovieListResponse
import ru.androidschool.intensiv.data.network.mapper.MovieMapper
import ru.androidschool.intensiv.domain.entity.MovieCard

class NowPlayingMovieRepositoryImpl(private val movieApiInterface: MovieApiInterface) {

    fun getNowPlayingMovies(callback: (CustomResult<List<MovieCard>>) -> Unit) {
        callback(CustomResult.Loading)

        movieApiInterface.getNowPlayingMovies().enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(call: Call<MovieListResponse>, response: Response<MovieListResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results?.map { MovieMapper.mapToMovieCardEntity(it) } ?: emptyList()
                    callback(CustomResult.Success(movies))
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
