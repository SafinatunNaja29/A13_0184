package com.example.finalproject.ui.viewmodel.Peserta

import com.example.finalproject.model.Peserta

data class InsertPesertaUiState(
    val insertPesertaUiEvent: InsertPesertaUiEvent = InsertPesertaUiEvent(),
    val error: String? = null
)
data class InsertPesertaUiEvent(
    val id_peserta: String = "",
    val nama_peserta: String = "",
    val email: String = "",
    val nomor_telepon: String = "",
)

fun InsertPesertaUiEvent.toPeserta(): Peserta = Peserta (
    id_peserta = id_peserta,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon
)

fun Peserta.toUiStatePeserta(): InsertPesertaUiState = InsertPesertaUiState(
    insertPesertaUiEvent = toInsertPesertaUiEvent()
)

fun Peserta.toInsertPesertaUiEvent(): InsertPesertaUiEvent = InsertPesertaUiEvent(
    id_peserta = id_peserta,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon
)