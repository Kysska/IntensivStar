package ru.androidschool.intensiv.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DatabaseConst.Movie.TABLE_NAME)
data class MovieDbEntity(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConst.Movie.COLUMN_ID)
    val id: Int,
    @ColumnInfo(name = DatabaseConst.Movie.COLUMN_TITLE)
    val title: String,
    @ColumnInfo(name = DatabaseConst.Movie.COLUMN_POSTER_PATH)
    val posterPath: String,
    @ColumnInfo(name = DatabaseConst.Movie.COLUMN_RATING)
    val rating: Double,
    @ColumnInfo(name = DatabaseConst.Movie.COLUMN_OVERVIEW)
    val overview: String,
    @ColumnInfo(name = DatabaseConst.Movie.COLUMN_PRODUCTION_COMPANIES)
    val productionCompanies: String,
    @ColumnInfo(name = DatabaseConst.Movie.COLUMN_GENRE)
    val genre: String,
    @ColumnInfo(name = DatabaseConst.Movie.COLUMN_RELEASE_DATE)
    val releaseDate: String
)
