package ru.androidschool.intensiv.data.network.source

import io.reactivex.Single
import ru.androidschool.intensiv.data.repository.cast.RemoteCastDataSource
import ru.androidschool.intensiv.data.repository.moviedetail.RemoteMovieDetailDataSource
import ru.androidschool.intensiv.data.repository.moviewithcast.RemoteMovieWithCastDataSource
import ru.androidschool.intensiv.domain.entity.MovieWithCast

class RemoteMovieWithCastDataSourceImpl(
    private val movieDetailDataSource: RemoteMovieDetailDataSource,
    private val castDataSource: RemoteCastDataSource
) : RemoteMovieWithCastDataSource {
    override fun fetchingMovieWithCast(id: Int): Single<MovieWithCast> {
        return Single.zip(
            movieDetailDataSource.fetchMovieDetail(id),
            castDataSource.fetchCasts(id)
        ) { movieDetail, casts ->

            MovieWithCast(
                movie = movieDetail,
                cast = casts
            )
        }
    }
}
