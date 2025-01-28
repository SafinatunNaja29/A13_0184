package com.example.finalproject.service

import com.example.finalproject.model.Peserta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PesertaService{
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("bacapeserta.php")
    suspend fun getAllPeserta(): List<Peserta>

    @GET("baca1peserta.php/{id_peserta}")
    suspend fun getPesertaById(@Query("id_peserta") id_peserta: String): Peserta

    @POST("insertpeserta.php")
    suspend fun insertPeserta(@Body peserta: Peserta)

    @PUT("editpeserta.php/{id_peserta}")
    suspend fun updatePeserta(@Query("id_peserta") id_peserta: String, @Body peserta: Peserta)

    @DELETE("deletepeserta.php/{id_peserta}")
    suspend fun deletePeserta(@Query("id_peserta") id_peserta: String): Response<Void>

}