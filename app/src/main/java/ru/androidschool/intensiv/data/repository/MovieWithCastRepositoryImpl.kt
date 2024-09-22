package ru.androidschool.intensiv.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.dto.MovieCastCrossRef
import ru.androidschool.intensiv.data.local.mapper.MovieWithCastMapper
import ru.androidschool.intensiv.domain.CastRepository
import ru.androidschool.intensiv.domain.MovieDetailRepository
import ru.androidschool.intensiv.domain.MovieWithCastRepository
import ru.androidschool.intensiv.domain.entity.MovieDetail
import ru.androidschool.intensiv.domain.entity.MovieWithCast
import timber.log.Timber

class MovieWithCastRepositoryImpl(
    private val movieDetailRepository: MovieDetailRepository,
    private val castRepository: CastRepository,
    private val movieDetailDao: MovieDetailDao
) : MovieWithCastRepository, BaseRepository<MovieWithCast>() {

    override fun getMovieWithCastFromNetwork(id: Int): Single<MovieWithCast> {
        return Single.zip(
            movieDetailRepository.getMovieDetail(id),
            castRepository.getCasts(id)
        ) { movieDetail, casts ->

            MovieWithCast(
                movie = movieDetail,
                cast = casts
            )
        }
    }

    override fun getMovieWithCastFromLocal(id: Int): Single<MovieWithCast> {
        return fetchData(
            call = { movieDetailDao.getMovieWithCast(id).doOnSuccess { Timber.d("Fetched from local: $it") } },
            mapper = { MovieWithCastMapper.reverseMap(it) },
            emptyResult = MovieWithCast(MovieDetail(), emptyList()),
            tag = REPOSITORY_TAG
        )
    }

    override fun saveMovieWithCast(movieWithCast: MovieWithCast): Completable {
        val movie = movieWithCast.movie
        val casts = movieWithCast.cast
        return movieDetailRepository.saveMovieDetail(movie)
            .andThen(castRepository.saveCast(casts))
            .andThen(
                Completable.fromAction {
                    casts.forEach { cast ->
                        val crossRef = MovieCastCrossRef(movie.id, cast.id)
                        movieDetailDao.insertMovieCastCrossRef(crossRef).subscribe()
                    }
                }
            )
            .doOnError { error ->
                Timber.e(error, "Error saving movie with cast")
            }
    }

    override fun deleteMovieWithCast(movieWithCast: MovieWithCast): Completable {
        return movieDetailRepository.deleteMovieDetail(movieWithCast.movie)
    }

    companion object {
        private const val REPOSITORY_TAG = "MovieWithCastRepository"
    }
}
