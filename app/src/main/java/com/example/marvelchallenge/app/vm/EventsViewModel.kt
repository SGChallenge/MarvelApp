package com.example.marvelchallenge.app.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.marvelchallenge.data.models.ExpandableEvent
import com.example.marvelchallenge.data.networking.Resource
import com.example.marvelchallenge.data.networking.Status
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.domain.usecases.EventsListUseCase
import com.example.marvelchallenge.utils.asExpandableEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(useCase: EventsListUseCase) : ViewModel() {

    private val eventList: LiveData<Resource<List<Event>>> = useCase.fetchEvents()

    private val expandableEvents: LiveData<List<ExpandableEvent>?> = eventList.map { r ->
        r.data?.let { list ->
            list.map { it.asExpandableEvent() }
        }
    }

    val loading = eventList.map { it.status == Status.LOADING }

    private val expandedEvents: MutableLiveData<List<String>> = MutableLiveData<List<String>>()

    val expandableEventList: LiveData<List<ExpandableEvent>?> =
        MediatorLiveData<List<ExpandableEvent>?>().apply {
            addSource(expandableEvents) {
                value = getExpandedList(it, expandedEvents.value)
            }
            addSource(expandedEvents) {
                value = getExpandedList(expandableEvents.value, it)
            }
        }

    fun setEventIsExpanded(id: String) {
        expandedEvents.value = if (expandedEvents.value?.contains(id) == true) {
            expandedEvents.value?.minus(id)
        } else {
            (expandedEvents.value ?: emptyList()).plus(id)
        }
    }

    private fun getExpandedList(
        expandableEvents: List<ExpandableEvent>?,
        expandedEvents: List<String>?
    ): List<ExpandableEvent>? =
        expandableEvents?.map { event ->
            event.copy(isExpanded = expandedEvents?.find { it == event.id } != null)
        }
}
