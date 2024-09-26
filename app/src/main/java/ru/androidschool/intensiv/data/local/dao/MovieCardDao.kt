package ru.androidschool.intensiv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import ru.androidschool.intensiv.data.local.dto.MovieCardDbEntity

@Dao
interface MovieCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieCardDbEntity): Completable

    @Query("SELECT * FROM movies_card ORDER BY created_at LIMIT 20")
    fun getAllMoviesCard(): Single<List<MovieCardDbEntity>>

    @Query("SELECT * FROM movies_card WHERE category = :category ORDER BY created_at LIMIT 20")
    fun getAllMoviesCardByCategory(category: String): Single<List<MovieCardDbEntity>>
}
