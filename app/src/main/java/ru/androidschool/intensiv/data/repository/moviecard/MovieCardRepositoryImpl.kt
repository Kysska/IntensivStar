package ru.androidschool.intensiv.data.repository.moviecard

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.domain.MovieRepository
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.utils.MovieType
import timber.log.Timber

class MovieCardRepositoryImpl(
    private val remoteMovieCardDataSource: RemoteMovieCardDataSource,
    private val localMovieCardDataSource: LocalMovieCardDataSource
) : MovieRepository {

    override fun getMovies(category: MovieType): Single<List<MovieCard>> {
        return remoteMovieCardDataSource.fetchListMovieCard(category)
            .flatMap { networkData ->
                Completable.merge(
                    networkData.map { movie ->
                        saveMovie(movie, category)
                    }
                ).andThen(Single.just(networkData))
            }
            .onErrorResumeNext { throwable ->
                Timber.tag("MovieCardRepositoryImpl").e(throwable, "Error fetching movies from network, loading from local")
                localMovieCardDataSource.getListMovieCard(category) }
    }

    override fun saveMovie(movieCard: MovieCard, category: MovieType): Completable {
        return localMovieCardDataSource.saveMovieCard(movieCard, category)
    }
}
