package ru.androidschool.intensiv.data.local.dto

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithCastDb(
    @Embedded
    val movie: MovieDbEntity,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "cast_id",
        associateBy = Junction(MovieCastCrossRef::class)
    )
    val cast: List<CastDbEntity>
)
