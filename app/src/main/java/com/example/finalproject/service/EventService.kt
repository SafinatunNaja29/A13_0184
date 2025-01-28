package com.example.finalproject.service

import com.example.finalproject.model.Event
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface EventService{
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("bacaevent.php")
    suspend fun getAllEvent(): List<Event>

    @GET("baca1event.php/{id_event}")
    suspend fun getEventById(@Query("id_event") id_event: String): Event

    @POST("insertevent.php")
    suspend fun insertEvent(@Body event: Event)

    @PUT("editevent.php/{id_event}")
    suspend fun updateEvent(@Query("id_event") id_event: String, @Body event: Event)

    @DELETE("deleteevent.php/{id_event}")
    suspend fun deleteEvent(@Query("id_event") id_event: String): Response<Void>

}
