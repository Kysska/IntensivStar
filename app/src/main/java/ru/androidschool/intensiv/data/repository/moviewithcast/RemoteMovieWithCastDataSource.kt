package ru.androidschool.intensiv.data.repository.moviewithcast

import io.reactivex.Single
import ru.androidschool.intensiv.domain.entity.MovieWithCast

interface RemoteMovieWithCastDataSource {
    fun fetchingMovieWithCast(id: Int): Single<MovieWithCast>
}
