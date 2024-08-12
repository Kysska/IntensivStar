package ru.androidschool.intensiv.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.androidschool.intensiv.data.network.interceptor.AuthTokenInterceptor

object MovieApiClient {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private var client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthTokenInterceptor())
        .addInterceptor(HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val apiClient: MovieApiInterface by lazy {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return@lazy retrofit.create(MovieApiInterface::class.java)
    }
}
