package ru.androidschool.intensiv.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.androidschool.intensiv.data.local.dao.MovieCardDao
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.database.MovieDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): MovieDatabase {
        return MovieDatabase.invoke(context)
    }

    @Provides
    @Singleton
    fun provideMovieCardDao(database: MovieDatabase): MovieCardDao {
        return database.movieCardDao()
    }

    @Provides
    @Singleton
    fun provideMovieDetailDao(database: MovieDatabase): MovieDetailDao {
        return database.movieDetailDao()
    }
}
