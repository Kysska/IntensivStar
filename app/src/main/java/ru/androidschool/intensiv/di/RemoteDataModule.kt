package ru.androidschool.intensiv.di

import dagger.Module
import dagger.Provides
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.network.mapper.CastCardNetworkMapper
import ru.androidschool.intensiv.data.network.mapper.MovieCardNetworkMapper
import ru.androidschool.intensiv.data.network.mapper.MovieDetailNetworkMapper
import ru.androidschool.intensiv.data.network.source.RemoteCastDataSourceImpl
import ru.androidschool.intensiv.data.network.source.RemoteMovieCardDataSourceImpl
import ru.androidschool.intensiv.data.network.source.RemoteMovieDetailDataSourceImpl
import ru.androidschool.intensiv.data.network.source.RemoteMovieWithCastDataSourceImpl
import ru.androidschool.intensiv.data.repository.cast.RemoteCastDataSource
import ru.androidschool.intensiv.data.repository.moviecard.RemoteMovieCardDataSource
import ru.androidschool.intensiv.data.repository.moviedetail.RemoteMovieDetailDataSource
import ru.androidschool.intensiv.data.repository.moviewithcast.RemoteMovieWithCastDataSource
import javax.inject.Singleton

@Module
class RemoteDataModule {

    @Provides
    @Singleton
    fun provideMovieCardRemoteDataSource(): RemoteMovieCardDataSource {
        return RemoteMovieCardDataSourceImpl(
            MovieApiClient.apiClient,
            MovieCardNetworkMapper
        )
    }

    @Provides
    @Singleton
    fun provideMovieDetailRemoteDataSource(): RemoteMovieDetailDataSource {
        return RemoteMovieDetailDataSourceImpl(
            MovieApiClient.apiClient,
            MovieDetailNetworkMapper
        )
    }

    @Provides
    @Singleton
    fun provideCastRemoteDataSource(): RemoteCastDataSource {
        return RemoteCastDataSourceImpl(
            MovieApiClient.apiClient,
            CastCardNetworkMapper
        )
    }

    @Provides
    @Singleton
    fun provideMovieWithCastRemoteDataSource(
        castDataSource: RemoteCastDataSource,
        movieDetailDataSource: RemoteMovieDetailDataSource
    ): RemoteMovieWithCastDataSource {
        return RemoteMovieWithCastDataSourceImpl(
            movieDetailDataSource,
            castDataSource
        )
    }
}
