package com.example.marvelchallenge.domain.repositories

import com.example.marvelchallenge.BuildConfig
import com.example.marvelchallenge.data.dao.EventDao
import com.example.marvelchallenge.data.networking.MarvelService
import com.example.marvelchallenge.data.networking.Resource
import com.example.marvelchallenge.data.networking.networkBoundResource
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.utils.toMD5
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventsRepository @Inject constructor(
    private val marvelService: MarvelService,
    private val dao: EventDao
) {
    fun fetchEvents(): Flow<Resource<List<Event>>> {
        val ts = System.currentTimeMillis()
        return networkBoundResource(
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
}
