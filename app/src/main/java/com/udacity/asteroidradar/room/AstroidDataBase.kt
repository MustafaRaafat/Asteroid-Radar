package com.udacity.asteroidradar.room

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid

@Dao
interface AstroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAstroid(vararg asteroid: Asteroid)

    @Query("SELECT * FROM asteroid")
    suspend fun getAstroidFromDB(): List<Asteroid>
}

@Database(entities = [Asteroid::class], version = 1)
abstract class AsteroidDb : RoomDatabase() {
    abstract val dao: AstroidDao
}

private lateinit var Ins: AsteroidDb
fun getDatabase(context: Context): AsteroidDb {
    synchronized(AsteroidDb::class.java) {
        if (!::Ins.isInitialized) {
            Ins =
                Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidDb::class.java,
                    "Asteroid_db"
                )
                    .build()
        }
    }
    return Ins
}