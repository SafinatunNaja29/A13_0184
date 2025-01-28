package com.example.finalproject.model

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id_event: String,
    val nama_event: String,
    val deskripsi_event: String,
    val tanggal_event: String,
    val lokasi_event: String
)