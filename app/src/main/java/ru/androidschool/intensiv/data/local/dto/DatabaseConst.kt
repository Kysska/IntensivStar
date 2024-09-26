package ru.androidschool.intensiv.data.local.dto

object DatabaseConst {

    object Cast {
        const val TABLE_NAME = "cast"
        const val COLUMN_ID = "cast_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_POSTER_PATH = "poster_path"
    }

    object MovieCard {
        const val TABLE_NAME = "movies_card"
        const val COLUMN_ID = "movie_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_POSTER_PATH = "poster_path"
        const val COLUMN_RATING = "rating"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_CREATED_AT = "created_at"
    }

    object MovieCastCrossRef {
        const val TABLE_NAME = "movie_cast_cross_ref"
        const val COLUMN_MOVIE_ID = "movie_id"
        const val COLUMN_CAST_ID = "cast_id"
    }

    object Movie {
        const val TABLE_NAME = "movies"
        const val COLUMN_ID = "movie_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_POSTER_PATH = "poster_path"
        const val COLUMN_RATING = "rating"
        const val COLUMN_OVERVIEW = "overview"
        const val COLUMN_PRODUCTION_COMPANIES = "production_companies"
        const val COLUMN_GENRE = "genre"
        const val COLUMN_RELEASE_DATE = "release_date"
    }
}
