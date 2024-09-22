package ru.androidschool.intensiv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe
import ru.androidschool.intensiv.data.local.dto.FavoriteStatus

@Dao
interface FavoriteStatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setFavoriteStatus(favoriteStatus: FavoriteStatus): Completable

    @Query("SELECT is_favorite FROM favorite_status WHERE id = :movieId")
    fun getFavoriteStatus(movieId: Int): Maybe<Boolean>
}
