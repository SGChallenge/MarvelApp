package com.example.marvelchallenge.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.marvelchallenge.data.networking.Resource
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.domain.repositories.EventsRepository

interface EventsListUseCase {
    fun fetchEvents(): LiveData<Resource<List<Event>>>
}

class EventsListUseCaseImpl(private val repo: EventsRepository) : EventsListUseCase {
    override fun fetchEvents(): LiveData<Resource<List<Event>>> =
        repo.fetchEvents().asLiveData()
}
