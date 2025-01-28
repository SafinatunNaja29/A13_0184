package com.example.finalproject.repository

import com.example.finalproject.model.Tiket
import com.example.finalproject.service.TiketService
import okio.IOException

interface TiketRepository{
    suspend fun getTiket(): List<Tiket>
    suspend fun insertTiket(tiket: Tiket)
    suspend fun updateTiket(id_tiket: String, tiket: Tiket)
    suspend fun deleteTiket(id_tiket: String)
    suspend fun getTiketById(id_tiket: String): Tiket
}

class NetworkTiketRepository(
    private val tiketApiService: TiketService
): TiketRepository {
    override suspend fun getTiket(): List<Tiket> =
        tiketApiService.getAllTiket()

    override suspend fun insertTiket(tiket: Tiket) {
        tiketApiService.insertTiket(tiket)
    }

    override suspend fun updateTiket(id_tiket: String, tiket: Tiket) {
        tiketApiService.updateTiket(id_tiket, tiket)
    }

    override suspend fun deleteTiket(id_tiket: String) {
        try{
            val response = tiketApiService.deleteTiket(id_tiket)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Tiket. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }

    override suspend fun getTiketById(id_tiket: String): Tiket {
        return tiketApiService.getTiketById(id_tiket)
    }

}