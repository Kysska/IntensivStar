package ru.androidschool.intensiv.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.mapper.MovieDetailDatabaseMapper
import ru.androidschool.intensiv.data.network.MovieApiInterface
import ru.androidschool.intensiv.data.network.mapper.MovieDetailNetworkMapper
import ru.androidschool.intensiv.data.repository.base.BaseRepository
import ru.androidschool.intensiv.domain.MovieDetailRepository
import ru.androidschool.intensiv.domain.entity.MovieDetail

class MovieDetailRepositoryImpl(
    private val movieApiInterface: MovieApiInterface,
    private val movieDetailDao: MovieDetailDao,
    private val networkMapper: MovieDetailNetworkMapper,
    private val databaseMapper: MovieDetailDatabaseMapper
) :
    BaseRepository<MovieDetail>(), MovieDetailRepository {

    override fun getMovieDetail(id: Int): Single<MovieDetail> {
        return fetchData(
            call = { movieApiInterface.getMovieDetailById(id) },
            mapper = { response -> networkMapper.map(response) },
            emptyResult = MovieDetail(),
            tag = REPOSITORY_TAG
        )
    }

    override fun saveMovieDetail(movieDetail: MovieDetail): Completable {
        val movieDbEntity = databaseMapper.map(movieDetail)
        return performDatabaseOperation(
            daoOperation = { movieDetailDao.insertMovie(movieDbEntity) },
            operationType = "Insert",
            tag = REPOSITORY_TAG
        )
    }

    override fun deleteMovieDetail(movieDetail: MovieDetail): Completable {
        val movieDbEntity = databaseMapper.map(movieDetail)
        return performDatabaseOperation(
            daoOperation = { movieDetailDao.deleteMovie(movieDbEntity) },
            operationType = "Delete",
            tag = REPOSITORY_TAG
        )
    }

    companion object {
        const val REPOSITORY_TAG = "MovieDetail"
    }
}
