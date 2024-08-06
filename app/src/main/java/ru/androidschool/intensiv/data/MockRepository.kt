package ru.androidschool.intensiv.data

object MockRepository {

    fun getMovies(): List<Movie> {

        val moviesList = mutableListOf<Movie>()
        for (x in 0..10) {
            val movie = Movie(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x
            )
            moviesList.add(movie)
        }

        return moviesList
    }

    fun getTvShow(): List<TvShow> {

        val tvShowList = mutableListOf<TvShow>()
        for (x in 0..10) {
            val tvShow = TvShow(
                title = "Stranger Things",
                voteAverage = 10.0 - x,
                image = "https://avatars.mds.yandex.net/i?id=a355c4e4f8d9c076a64f6795cb91f6bc_l-5268598-images-thumbs&n=13"
            )
            tvShowList.add(tvShow)
        }

        return tvShowList
    }
}
