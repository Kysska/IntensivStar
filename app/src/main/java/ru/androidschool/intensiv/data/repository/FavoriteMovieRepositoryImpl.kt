package ru.androidschool.intensiv.data.repository

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import ru.androidschool.intensiv.data.local.dao.FavoriteStatusDao
import ru.androidschool.intensiv.data.local.dao.MovieCardDao
import ru.androidschool.intensiv.data.local.dto.FavoriteStatus
import ru.androidschool.intensiv.data.local.mapper.MovieCardDatabaseMapper
import ru.androidschool.intensiv.domain.FavoriteMovieRepository
import ru.androidschool.intensiv.domain.entity.MovieCard

class FavoriteMovieRepositoryImpl(
    private val movieCardDao: MovieCardDao,
    private val isFavoriteStatusDao: FavoriteStatusDao
) : BaseRepository<List<MovieCard>>(), FavoriteMovieRepository {

    override fun getAllFavoriteMovieList(): Observable<List<MovieCard>> {
        return fetchData(
            call = { movieCardDao.getAllFavoriteMoviesCard() },
            mapper = { MovieCardDatabaseMapper.reverseMap(it) },
            tag = REPOSITORY_TAG
        )
    }

    override fun getIsMovieFavorite(movieId: Int): Maybe<Boolean> {
        return isFavoriteStatusDao.getFavoriteStatus(movieId)
    }

    override fun setIsMovieFavorite(movieId: Int, isFavorite: Boolean): Completable {
        return isFavoriteStatusDao.setFavoriteStatus(FavoriteStatus(movieId, isFavorite))
    }

    companion object {
        private const val REPOSITORY_TAG = "FavoriteMovieRepositoryImpl"
    }
}
