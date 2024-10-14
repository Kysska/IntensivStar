package ru.androidschool.intensiv.di

import dagger.Module
import dagger.Provides
import ru.androidschool.intensiv.data.local.dao.MovieCardDao
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.mapper.CastDatabaseMapper
import ru.androidschool.intensiv.data.local.mapper.MovieCardDatabaseMapper
import ru.androidschool.intensiv.data.local.mapper.MovieDetailDatabaseMapper
import ru.androidschool.intensiv.data.local.mapper.MovieWithCastMapper
import ru.androidschool.intensiv.data.local.source.LocalCastDataSourceImpl
import ru.androidschool.intensiv.data.local.source.LocalMovieCardDataSourceImpl
import ru.androidschool.intensiv.data.local.source.LocalMovieDetailDataSourceImpl
import ru.androidschool.intensiv.data.local.source.LocalMovieWithCastDataSourceImpl
import ru.androidschool.intensiv.data.repository.cast.LocalCastDataSource
import ru.androidschool.intensiv.data.repository.moviecard.LocalMovieCardDataSource
import ru.androidschool.intensiv.data.repository.moviedetail.LocalMovieDetailDataSource
import ru.androidschool.intensiv.data.repository.moviewithcast.LocalMovieWithCastDataSource
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideMovieCardLocalDataSource(movieCardDao: MovieCardDao): LocalMovieCardDataSource {
        return LocalMovieCardDataSourceImpl(
            movieCardDao,
            MovieCardDatabaseMapper
        )
    }

    @Provides
    @Singleton
    fun provideMovieDetailLocalDataSource(movieDetailDao: MovieDetailDao): LocalMovieDetailDataSource {
        return LocalMovieDetailDataSourceImpl(
            movieDetailDao,
            MovieDetailDatabaseMapper
        )
    }

    @Provides
    @Singleton
    fun provideCastLocalDataSource(castDao: MovieDetailDao): LocalCastDataSource {
        return LocalCastDataSourceImpl(
            castDao,
            CastDatabaseMapper
        )
    }

    @Provides
    @Singleton
    fun provideMovieWithCastLocalDataSource(
        movieDetailDataSource: LocalMovieDetailDataSource,
        castDataSource: LocalCastDataSource,
        movieDetailDao: MovieDetailDao
    ): LocalMovieWithCastDataSource {
        return LocalMovieWithCastDataSourceImpl(
            movieDetailDataSource,
            castDataSource,
            movieDetailDao,
            MovieWithCastMapper
        )
    }
}
