package ru.androidschool.intensiv.data.network.source

import io.reactivex.Single
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.MovieDetailNetworkMapper
import ru.androidschool.intensiv.data.repository.moviedetail.RemoteMovieDetailDataSource
import ru.androidschool.intensiv.domain.entity.MovieDetail
import timber.log.Timber

class RemoteMovieDetailDataSourceImpl(
    private val movieApiInterface: MovieApiInterface,
    private val networkMapper: MovieDetailNetworkMapper
) : RemoteMovieDetailDataSource {

    override fun fetchMovieDetail(id: Int): Single<MovieDetail> {
        return movieApiInterface.getMovieDetailById(id)
            .map { response -> networkMapper.map(response) }
            .doOnError { throwable ->
                Timber.tag("MovieDetail").e(throwable)
            }
    }
}
