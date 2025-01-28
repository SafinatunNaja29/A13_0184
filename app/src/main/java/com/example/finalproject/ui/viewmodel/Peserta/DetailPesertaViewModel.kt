package com.example.finalproject.ui.viewmodel.Peserta

import com.example.finalproject.model.Peserta



data class DetailPesertaUiState(
    val detailPesertaUiEvent: InsertPesertaUiEvent = InsertPesertaUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiPesertaEmpty: Boolean
        get() = detailPesertaUiEvent == InsertPesertaUiEvent()

    val isUiPesertaNotEmpty: Boolean
        get() = detailPesertaUiEvent != InsertPesertaUiEvent()
}

fun Peserta.toDetailPesertaUiEvent(): InsertPesertaUiEvent {
    return InsertPesertaUiEvent(
        id_peserta = id_peserta,
        nama_peserta = nama_peserta,
        email = email,
        nomor_telepon = nomor_telepon
    )
}