package ru.androidschool.intensiv.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.androidschool.intensiv.data.local.dao.MovieCardDao
import ru.androidschool.intensiv.data.local.dao.MovieDetailDao
import ru.androidschool.intensiv.data.local.dto.CastDbEntity
import ru.androidschool.intensiv.data.local.dto.MovieCardDbEntity
import ru.androidschool.intensiv.data.local.dto.MovieCastCrossRef
import ru.androidschool.intensiv.data.local.dto.MovieDbEntity

@Database(
    version = 1,
    entities = [MovieDbEntity::class, CastDbEntity::class, MovieCastCrossRef::class, MovieCardDbEntity::class]
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDetailDao(): MovieDetailDao
    abstract fun movieCardDao(): MovieCardDao

    companion object {
        private const val DATABASE_NAME = "FavoriteMovieDatabase"
        private lateinit var INSTANCE: MovieDatabase

        @Synchronized
        operator fun invoke(context: Context): MovieDatabase {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    DATABASE_NAME
                )
                    .build()
            }
            return INSTANCE
        }
    }
}
