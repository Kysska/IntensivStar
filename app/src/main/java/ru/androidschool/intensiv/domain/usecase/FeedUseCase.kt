package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Single
import ru.androidschool.intensiv.domain.entity.MovieCard
import ru.androidschool.intensiv.utils.MovieType

class FeedUseCase(
    private val getMoviesUseCase: GetMoviesUseCase
) {
    fun execute(): Single<Map<MovieType, List<MovieCard>>>{
        val nowPlaying = getMoviesUseCase.execute(MovieType.NOW_PLAYING)
        val popular = getMoviesUseCase.execute(MovieType.POPULAR)
        val upcoming = getMoviesUseCase.execute(MovieType.UPCOMING)

        return Single.zip(nowPlaying, popular, upcoming) { nowPlayingList, popularList, upcomingList ->
            mapOf(
                MovieType.NOW_PLAYING to nowPlayingList,
                MovieType.POPULAR to popularList,
                MovieType.UPCOMING to upcomingList
            )
        }
    }
}