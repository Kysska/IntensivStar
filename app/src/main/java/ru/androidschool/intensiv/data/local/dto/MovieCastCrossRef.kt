package ru.androidschool.intensiv.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "movie_cast_cross_ref",
    primaryKeys = ["movie_id", "cast_id"]
)
data class MovieCastCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "cast_id")
    val castId: Int
)
