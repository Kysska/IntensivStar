package ru.androidschool.intensiv.data.local.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromInt(value: Int): Boolean {
        return value == 1
    }

    @TypeConverter
    fun toInt(value: Boolean): Int {
        return if (value) 1 else 0
    }
}
