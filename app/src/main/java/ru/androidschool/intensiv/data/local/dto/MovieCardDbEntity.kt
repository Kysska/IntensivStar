package ru.androidschool.intensiv.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DatabaseConst.MovieCard.TABLE_NAME)
data class MovieCardDbEntity(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConst.MovieCard.COLUMN_ID)
    val id: Int,
    @ColumnInfo(name = DatabaseConst.MovieCard.COLUMN_TITLE)
    val title: String,
    @ColumnInfo(name = DatabaseConst.MovieCard.COLUMN_POSTER_PATH)
    val posterPath: String,
    @ColumnInfo(name = DatabaseConst.MovieCard.COLUMN_RATING)
    val rating: Double,
    @ColumnInfo(name = DatabaseConst.MovieCard.COLUMN_CATEGORY)
    val category: String,
    @ColumnInfo(name = DatabaseConst.MovieCard.COLUMN_CREATED_AT)
    val createdAt: Long = System.currentTimeMillis()
)
