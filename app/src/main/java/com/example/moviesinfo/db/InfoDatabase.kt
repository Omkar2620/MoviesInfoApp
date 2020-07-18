package com.example.moviesinfo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesinfo.model.Info

@Database(
    entities = [Info::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class InfoDatabase : RoomDatabase(){

    abstract fun getInfoDao(): InfoDAO

    companion object {

        @Volatile
        private var instance: InfoDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: createDatabase(context).also{ instance = it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                InfoDatabase::class.java,
                "movies_db.db"
            ).build()
    }
}