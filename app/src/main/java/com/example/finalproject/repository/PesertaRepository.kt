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

