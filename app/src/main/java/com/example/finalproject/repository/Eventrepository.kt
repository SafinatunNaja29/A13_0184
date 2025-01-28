package com.example.finalproject.repository

import com.example.finalproject.model.Event
import com.example.finalproject.service.EventService
import okio.IOException

interface EventRepository{
    suspend fun getEvent(): List<Event>
    suspend fun insertEvent(event: Event)
    suspend fun updateEvent(id_event: String, event: Event)
    suspend fun deleteEvent(id_event: String)
    suspend fun getEventById(id_event: String): Event
}

class NetworkEventRepository(
    private val eventApiService: EventService
): EventRepository {
    override suspend fun getEvent(): List<Event> =
        eventApiService.getAllEvent()

    override suspend fun insertEvent(event: Event) {
        eventApiService.insertEvent(event)
    }

    override suspend fun updateEvent(id_event: String, event: Event) {
        eventApiService.updateEvent(id_event, event)
    }

    override suspend fun deleteEvent(id_event: String) {
        try{
            val response = eventApiService.deleteEvent(id_event)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Event. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }

    override suspend fun getEventById(id_event: String): Event {
        return eventApiService.getEventById(id_event)
    }

}