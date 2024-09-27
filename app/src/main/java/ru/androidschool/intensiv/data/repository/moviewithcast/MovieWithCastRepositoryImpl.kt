package ru.androidschool.intensiv.data.repository.moviewithcast

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.domain.MovieWithCastRepository
import ru.androidschool.intensiv.domain.entity.MovieWithCast
import timber.log.Timber

class MovieWithCastRepositoryImpl(
    private val remoteMovieWithCastDataSource: RemoteMovieWithCastDataSource,
    private val localMovieWithCastDataSource: LocalMovieWithCastDataSource
) : MovieWithCastRepository {

    override fun getMovieWithCast(id: Int): Single<MovieWithCast> {
        return remoteMovieWithCastDataSource.fetchingMovieWithCast(id)
            .onErrorResumeNext { throwable ->
                Timber.tag("MovieWithCastRepositoryImpl").e(throwable, "Error fetching movies from network, loading from local")
                localMovieWithCastDataSource.getMovieWithCast(id)
            }
    }

    override fun saveMovieWithCast(movieWithCast: MovieWithCast): Completable {
        return localMovieWithCastDataSource.saveMovieWithCast(movieWithCast)
    }

    override fun deleteMovieWithCast(movieWithCast: MovieWithCast): Completable {
        return localMovieWithCastDataSource.deleteMovieWithCast(movieWithCast)
    }

    override fun isMovieExists(id: Int): Single<Boolean> {
        return localMovieWithCastDataSource.isMovieExists(id)
    }
}
