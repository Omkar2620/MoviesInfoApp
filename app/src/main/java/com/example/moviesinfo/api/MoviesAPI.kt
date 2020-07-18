package com.example.moviesinfo.api

import com.example.moviesinfo.model.Info
import com.example.moviesinfo.other.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    @GET("/")
    suspend fun getMovieInfo(
        @Query("t")
        search: String,
        @Query("apikey")
        apikey: String = API_KEY
    ):Response<Info>
}