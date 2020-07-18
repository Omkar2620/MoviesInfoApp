package com.example.moviesinfo.repositories

import com.example.moviesinfo.api.RetrofitInstance
import com.example.moviesinfo.db.InfoDatabase
import com.example.moviesinfo.model.Info

class MoviesRepository(
    val db:InfoDatabase
) {

    suspend fun getMoviesFromAPI(searchQuery: String) = RetrofitInstance.api.getMovieInfo(searchQuery)

    fun getSavedMovies() = db.getInfoDao().getAllSavedMovies()

    suspend fun saveMovie(info: Info) = db.getInfoDao().upsert(info)

    suspend fun deleteMovie(info: Info) = db.getInfoDao().deleteMovie(info)
}