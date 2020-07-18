package com.example.moviesinfo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviesinfo.model.Info

@Dao
interface InfoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(info: Info):Long

    @Delete
    suspend fun deleteMovie(info: Info)

    @Query("SELECT * FROM movies_db")
    fun getAllSavedMovies(): LiveData<List<Info>>

}