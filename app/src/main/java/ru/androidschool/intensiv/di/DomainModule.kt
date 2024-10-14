package ru.androidschool.intensiv.di

import dagger.Module
import dagger.Provides
import ru.androidschool.intensiv.domain.MovieRepository
import ru.androidschool.intensiv.domain.usecase.FeedUseCase
import ru.androidschool.intensiv.domain.usecase.GetMoviesUseCase
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideGetMoviesUseCase(repository: MovieRepository): GetMoviesUseCase {
        return GetMoviesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFeedUseCase(getMoviesUseCase: GetMoviesUseCase): FeedUseCase {
        return FeedUseCase(getMoviesUseCase)
    }
}
