package com.example.finalproject.service

import com.example.finalproject.model.Transaksi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TransaksiService{
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("bacatransaksi.php")
    suspend fun getAllTransaksi(): List<Transaksi>

    @GET("baca1transaksi.php/{id_transaksi}")
    suspend fun getTransaksiById(@Query("id_transaksi") id_transaksi: String): Transaksi

    @POST("inserttransaksi.php")
    suspend fun insertTransaksi(@Body transaksi: Transaksi)

    @PUT("edittransaksi.php/{id_transaksi}")
    suspend fun updateTransaksi(@Query("id_transaksi") id_transaksi: String, @Body transaksi: Transaksi)

    @DELETE("deletetransaksi.php/{id_transaksi}")
    suspend fun deleteTransaksi(@Query("id_transaksi") id_transaksi: String): Response<Void>

}