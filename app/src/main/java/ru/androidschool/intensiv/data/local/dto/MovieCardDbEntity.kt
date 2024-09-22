package ru.androidschool.intensiv.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_card")
data class MovieCardDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val id: Int,
    val title: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    val rating: Double,
    val category: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
