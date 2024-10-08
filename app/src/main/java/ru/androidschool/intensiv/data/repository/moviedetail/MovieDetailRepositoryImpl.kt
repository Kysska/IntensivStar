package ru.androidschool.intensiv.data.repository.moviedetail

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.domain.MovieDetailRepository
import ru.androidschool.intensiv.domain.entity.MovieDetail

class MovieDetailRepositoryImpl(
    private val localMovieDetailDataSource: LocalMovieDetailDataSource,
    private val remoteMovieDetailDataSource: RemoteMovieDetailDataSource
) : MovieDetailRepository {

    override fun getMovieDetail(id: Int): Single<MovieDetail> {
        return remoteMovieDetailDataSource.fetchMovieDetail(id)
    }

    override fun saveMovieDetail(movieDetail: MovieDetail): Completable {
        return localMovieDetailDataSource.saveMovieDetail(movieDetail)
    }

    override fun deleteMovieDetail(movieDetail: MovieDetail): Completable {
        return localMovieDetailDataSource.deleteMovieDetail(movieDetail)
    }
}
