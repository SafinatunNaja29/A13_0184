package com.example.finalproject.repository

import com.example.finalproject.model.Event

interface EventRepository{
    suspend fun getEvent(): List<Event>
    suspend fun insertEvent(event: Event)
    suspend fun updateEvent(id_event: String, event: Event)
    suspend fun deleteEvent(id_event: String)
    suspend fun getEventById(id_event: String): Event
}