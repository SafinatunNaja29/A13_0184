package com.example.finalproject.model

import kotlinx.serialization.Serializable

@Serializable
data class Peserta(
    val id_peserta: String,
    val nama_peserta: String,
    val email: String,
    val nomor_telepon: String
)