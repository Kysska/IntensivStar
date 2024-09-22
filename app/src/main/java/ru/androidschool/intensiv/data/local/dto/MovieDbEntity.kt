package ru.androidschool.intensiv.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val id: Int,
    val title: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    val rating: Double,
    val overview: String,
    @ColumnInfo(name = "production_companies")
    val productionCompanies: String,
    val genre: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String
)
