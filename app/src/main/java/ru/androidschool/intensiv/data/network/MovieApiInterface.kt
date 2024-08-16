package ru.androidschool.intensiv.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.data.network.dto.CreditResponse
import ru.androidschool.intensiv.data.network.dto.MovieDetailResponse
import ru.androidschool.intensiv.data.network.dto.MovieListResponse
import java.util.Locale

interface MovieApiInterface {

    private val defaultLanguage: String
        get() = Locale.getDefault().toLanguageTag()

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("language") language: String = defaultLanguage
    ): Call<MovieListResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("language") language: String = defaultLanguage
    ): Call<MovieListResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("language") language: String = defaultLanguage
    ): Call<MovieListResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("language") language: String = defaultLanguage
    ): Call<MovieListResponse>

    @GET("tv/popular")
    fun getPopularTvShows(
        @Query("language") language: String = defaultLanguage
    ): Call<MovieListResponse>

    @GET("movie/{id}")
    fun getMovieDetailById(
        @Path("id") id: Int,
        @Query("language") language: String = defaultLanguage
    ): Call<MovieDetailResponse>

    @GET("movie/{id}/credits")
    fun getCastsListById(
        @Path("id") id: Int,
        @Query("language") language: String = defaultLanguage
    ): Call<CreditResponse>
}