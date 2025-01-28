package com.example.finalproject.ui.viewmodel.Peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Peserta
import com.example.finalproject.repository.PesertaRepository
import kotlinx.coroutines.launch

class DetailPesertaViewModel(
    savedStateHandle: SavedStateHandle,
    private val pesertaRepository: PesertaRepository
) : ViewModel() {
    val id_peserta: String = checkNotNull(savedStateHandle[DestinasiDetailPeserta.ID_PESERTA])

    var detailPesertaUiState: DetailPesertaUiState by mutableStateOf(DetailPesertaUiState())
        private set

    init {
        getPesertaById()
    }

    fun getPesertaById() {
        viewModelScope.launch {
            detailPesertaUiState = DetailPesertaUiState(isLoading = true)
            try {
                val result = pesertaRepository.getPesertaById(id_peserta)
                detailPesertaUiState = DetailPesertaUiState(
                    detailPesertaUiEvent = result.toDetailPesertaUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailPesertaUiState = DetailPesertaUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
}

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