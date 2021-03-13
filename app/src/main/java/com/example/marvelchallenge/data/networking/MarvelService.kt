package com.example.marvelchallenge.data.networking

import com.example.marvelchallenge.BuildConfig
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.domain.model.ObjectContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.http.GET
import retrofit2.http.Query

@OptIn(ExperimentalCoroutinesApi::class)
interface MarvelService {
    @GET("characters?orderBy=name&limit=15")
    suspend fun getPagedCharacters(
        @Query("ts") ts: Long,
        @Query("offset") offset: Int,
        @Query("hash") hash: String
    ): ObjectContainer<Character>

    @GET("events?orderBy=startDate&limit=25")
    suspend fun getEvents(
        @Query("ts") ts: Long,
        @Query("hash") hash: String
    ): ObjectContainer<Event>
}
