package ru.androidschool.intensiv.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.androidschool.intensiv.BuildConfig

class AuthTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("Authorization", BuildConfig.THE_MOVIE_DATABASE_API)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
