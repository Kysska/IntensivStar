package ru.androidschool.intensiv.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_status")
data class FavoriteStatus(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)
