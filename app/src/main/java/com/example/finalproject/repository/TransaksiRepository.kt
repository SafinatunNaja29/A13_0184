package com.example.finalproject.repository

import com.example.finalproject.model.Transaksi
import com.example.finalproject.service.TransaksiService
import okio.IOException

interface TransaksiRepository{
    suspend fun getTransaksi(): List<Transaksi>
    suspend fun insertTransaksi(transaksi: Transaksi)
    suspend fun updateTransaksi(id_transaksi: String, transaksi: Transaksi)
    suspend fun deleteTransaksi(id_transaksi: String)
    suspend fun getTransaksiById(id_transaksi: String): Transaksi
}

class NetworkTransaksiRepository(
    private val transaksiApiService: TransaksiService
): TransaksiRepository {
    override suspend fun getTransaksi(): List<Transaksi> =
        transaksiApiService.getAllTransaksi()

    override suspend fun insertTransaksi(transaksi: Transaksi) {
        transaksiApiService.insertTransaksi(transaksi)
    }

    override suspend fun updateTransaksi(id_transaksi: String, transaksi: Transaksi) {
        transaksiApiService.updateTransaksi(id_transaksi, transaksi)
    }

    override suspend fun deleteTransaksi(id_transaksi: String) {
        try{
            val response = transaksiApiService.deleteTransaksi(id_transaksi)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Transaksi. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }

    override suspend fun getTransaksiById(id_transaksi: String): Transaksi {
        return transaksiApiService.getTransaksiById(id_transaksi)
    }

}