package ru.androidschool.intensiv.data.repository.moviedetail

import io.reactivex.Single
import ru.androidschool.intensiv.domain.entity.MovieDetail

interface RemoteMovieDetailDataSource {

    fun fetchMovieDetail(id: Int): Single<MovieDetail>
}
