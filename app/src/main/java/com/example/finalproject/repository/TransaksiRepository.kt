package com.example.finalproject.repository

import com.example.finalproject.model.Transaksi

interface TransaksiRepository{
    suspend fun getTransaksi(): List<Transaksi>
    suspend fun insertTransaksi(transaksi: Transaksi)
    suspend fun updateTransaksi(id_transaksi: String, transaksi: Transaksi)
    suspend fun deleteTransaksi(id_transaksi: String)
    suspend fun getTransaksiById(id_transaksi: String): Transaksi
}