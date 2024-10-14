package ru.androidschool.intensiv.di

import dagger.Module
import dagger.Provides
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.mapper.MovieWithCastMapper
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.network.mapper.MovieCardNetworkMapper
import ru.androidschool.intensiv.data.repository.FavoriteMovieRepositoryImpl
import ru.androidschool.intensiv.data.repository.SearchRepositoryImpl
import ru.androidschool.intensiv.data.repository.moviecard.LocalMovieCardDataSource
import ru.androidschool.intensiv.data.repository.moviecard.MovieCardRepositoryImpl
import ru.androidschool.intensiv.data.repository.moviecard.RemoteMovieCardDataSource
import ru.androidschool.intensiv.data.repository.moviewithcast.LocalMovieWithCastDataSource
import ru.androidschool.intensiv.data.repository.moviewithcast.MovieWithCastRepositoryImpl
import ru.androidschool.intensiv.data.repository.moviewithcast.RemoteMovieWithCastDataSource
import ru.androidschool.intensiv.domain.FavoriteMovieRepository
import ru.androidschool.intensiv.domain.MovieRepository
import ru.androidschool.intensiv.domain.MovieWithCastRepository
import ru.androidschool.intensiv.domain.SearchRepository
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieCardRepository(
        localMovieCardDataSource: LocalMovieCardDataSource,
        remoteMovieCardDataSource: RemoteMovieCardDataSource
    ): MovieRepository {
        return MovieCardRepositoryImpl(remoteMovieCardDataSource, localMovieCardDataSource)
    }

    @Provides
    @Singleton
    fun provideMovieWithCastRepository(
        localMovieWithCastDataSource: LocalMovieWithCastDataSource,
        remoteMovieWithCastDataSource: RemoteMovieWithCastDataSource
    ): MovieWithCastRepository {
        return MovieWithCastRepositoryImpl(
            remoteMovieWithCastDataSource,
            localMovieWithCastDataSource
        )
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        movieDetailDao: MovieDetailDao
    ): FavoriteMovieRepository {
        return FavoriteMovieRepositoryImpl(
            movieDetailDao,
            MovieWithCastMapper
        )
    }

    @Provides
    @Singleton
    fun provideSearchRepository(): SearchRepository {
        return SearchRepositoryImpl(
            MovieApiClient.apiClient,
            MovieCardNetworkMapper
        )
    }
}
