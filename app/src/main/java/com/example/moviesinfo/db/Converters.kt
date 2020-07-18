package com.example.moviesinfo.db

import androidx.room.TypeConverter
import com.example.moviesinfo.model.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {

    @TypeConverter
    fun listToJson(value: List<Rating>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Rating>::class.java).toList()
}