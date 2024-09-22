package ru.androidschool.intensiv.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cast")
data class CastDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "cast_id")
    val id: Int,
    val name: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String
)
