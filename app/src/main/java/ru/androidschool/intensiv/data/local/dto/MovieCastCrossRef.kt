package ru.androidschool.intensiv.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = DatabaseConst.MovieCastCrossRef.TABLE_NAME,
    primaryKeys = [DatabaseConst.MovieCastCrossRef.COLUMN_MOVIE_ID, DatabaseConst.MovieCastCrossRef.COLUMN_CAST_ID]
)
data class MovieCastCrossRef(
    @ColumnInfo(name = DatabaseConst.MovieCastCrossRef.COLUMN_MOVIE_ID)
    val movieId: Int,
    @ColumnInfo(name = DatabaseConst.MovieCastCrossRef.COLUMN_CAST_ID)
    val castId: Int
)
