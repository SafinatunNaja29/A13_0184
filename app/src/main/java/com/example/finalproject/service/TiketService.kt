package com.example.finalproject.service

import com.example.finalproject.model.Tiket
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TiketService{
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("bacatiket.php")
    suspend fun getAllTiket(): List<Tiket>

    @GET("baca1tiket.php/{id_tiket}")
    suspend fun getTiketById(@Query("id_tiket") id_tiket: String): Tiket

    @POST("inserttiket.php")
    suspend fun insertTiket(@Body tiket: Tiket)

    @PUT("edittiket.php/{id_tiket}")
    suspend fun updateTiket(@Query("id_tiket") id_tiket: String, @Body tiket: Tiket)

    @DELETE("deletetiket.php/{id_tiket}")
    suspend fun deleteTiket(@Query("id_tiket") id_tiket: String): Response<Void>

}