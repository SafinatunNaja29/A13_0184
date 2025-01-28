package com.example.finalproject.repository

import com.example.finalproject.model.Tiket

interface TiketRepository{
    suspend fun getTiket(): List<Tiket>
    suspend fun insertTiket(tiket: Tiket)
    suspend fun updateTiket(id_tiket: String, tiket: Tiket)
    suspend fun deleteTiket(id_tiket: String)
    suspend fun getTiketById(id_tiket: String): Tiket
}