package com.example.marvelchallenge.data.db

import androidx.room.TypeConverter
import com.example.marvelchallenge.domain.model.ComicSummary
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {

    @TypeConverter
    fun restoreComicSummary(listOfString: String): List<ComicSummary> =
        GsonBuilder().create()
            .fromJson(listOfString, object : TypeToken<List<ComicSummary>>() {}.type)

    @TypeConverter
    fun saveComicSummary(list: List<ComicSummary>): String = Gson().toJson(list)

    @TypeConverter
    fun fromTimestamp(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time
}