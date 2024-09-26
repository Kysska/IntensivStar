package ru.androidschool.intensiv.data.local.dto

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithCastDb(
    @Embedded
    val movie: MovieDbEntity,
    @Relation(
        parentColumn = DatabaseConst.MovieCastCrossRef.COLUMN_MOVIE_ID,
        entityColumn = DatabaseConst.MovieCastCrossRef.COLUMN_CAST_ID,
        associateBy = Junction(MovieCastCrossRef::class)
    )
    val cast: List<CastDbEntity>
)
