package ru.androidschool.intensiv.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DatabaseConst.Cast.TABLE_NAME)
data class CastDbEntity(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConst.Cast.COLUMN_ID)
    val id: Int,
    @ColumnInfo(name = DatabaseConst.Cast.COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = DatabaseConst.Cast.COLUMN_POSTER_PATH)
    val posterPath: String
)
