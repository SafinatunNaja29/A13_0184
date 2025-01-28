package com.example.finalproject.repository

import com.example.finalproject.model.Peserta
import okio.IOException

interface PesertaRepository{
    suspend fun getPeserta(): List<Peserta>
    suspend fun insertPeserta(peserta: Peserta)
    suspend fun updatePeserta(id_peserta: String, peserta: Peserta)
    suspend fun deletePeserta(id_peserta: String)
    suspend fun getPesertaById(id_peserta: String): Peserta
}

class NetworkPesertaRepository(
    private val pesertaApiService: PesertaService
): PesertaRepository {
    override suspend fun getPeserta(): List<Peserta> =
        pesertaApiService.getAllPeserta()

    override suspend fun insertPeserta(peserta: Peserta) {
        pesertaApiService.insertPeserta(peserta)
    }

    override suspend fun updatePeserta(id_peserta: String, peserta: Peserta) {
        pesertaApiService.updatePeserta(id_peserta, peserta)
    }

    override suspend fun deletePeserta(id_peserta: String) {
        try{
            val response = pesertaApiService.deletePeserta(id_peserta)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Peserta. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }

    override suspend fun getPesertaById(id_peserta: String): Peserta {
        return pesertaApiService.getPesertaById(id_peserta)
    }

}