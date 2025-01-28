package com.example.finalproject.model

import kotlinx.serialization.Serializable

@Serializable
data class Tiket(
    val id_tiket: String,
    val id_event: String? = null,
    val id_peserta: String? = null,
    val nama_peserta: String,
    val nama_event: String,
    val kapasitas_tiket: String,
    val harga_tiket: String,
    val tanggal_event: String, //Tambahan
    val lokasi_event: String, //Tambahan

)