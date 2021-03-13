package com.example.marvelchallenge.domain.repositories

import com.example.marvelchallenge.BuildConfig
import com.example.marvelchallenge.data.dao.EventDao
import com.example.marvelchallenge.data.networking.MarvelService
import com.example.marvelchallenge.data.networking.networkBoundResource
import com.example.marvelchallenge.utils.toMD5
import javax.inject.Inject

class EventsRepository @Inject constructor(
    private val marvelService: MarvelService,
    private val dao: EventDao
) {
    private val ts = System.currentTimeMillis()

    fun fetchEvents() = networkBoundResource(
        query = { dao.load() },
        fetch = {
            marvelService.getEvents(
                ts,
                (ts.toString() + BuildConfig.PRIVATE_API_KEY + BuildConfig.API_KEY).toMD5()
            )
        },
        saveFetchResult = { dao.saveAllEvents(it.data.results) }
    )
}
