package ru.androidschool.intensiv.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.androidschool.intensiv.data.local.dto.CastDbEntity
import ru.androidschool.intensiv.data.local.dto.MovieCastCrossRef
import ru.androidschool.intensiv.data.local.dto.MovieDbEntity
import ru.androidschool.intensiv.data.local.dto.MovieWithCastDb

@Dao
interface MovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieDbEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCast(cast: CastDbEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieCastCrossRef(crossRef: MovieCastCrossRef): Completable

    @Delete
    fun deleteMovie(movie: MovieDbEntity): Completable

    @Delete
    fun deleteMovieCastCrossRef(crossRef: MovieCastCrossRef): Completable

    @Transaction
    @Query("SELECT * FROM movies")
    fun getAllFavoriteMoviesWithCast(): Observable<List<MovieWithCastDb>>

    @Transaction
    @Query("SELECT * FROM movies WHERE movie_id = :movieId")
    fun getMovieWithCast(movieId: Int): Single<MovieWithCastDb>

    @Query("SELECT 1 FROM movies WHERE movie_id = :id")
    fun isMovieExists(id: Int): Single<Boolean>
}
