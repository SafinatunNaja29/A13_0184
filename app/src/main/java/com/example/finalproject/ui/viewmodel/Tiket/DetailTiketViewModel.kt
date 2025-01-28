package com.example.finalproject.ui.viewmodel.Tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Tiket
import com.example.finalproject.repository.TiketRepository
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiDetailTiket: DestinasiNavigasi {
    override val route = "detail tiket"
    const val ID_TIKET = "id_tiket"
    override val titleRes = "Detail Tiket"
    val routeWithArg = "$route/{$ID_TIKET}"
}

class DetailTiketViewModel(
    savedStateHandle: SavedStateHandle,
    private val tiketRepository: TiketRepository
) : ViewModel() {
    val id_tiket: String = checkNotNull(savedStateHandle[DestinasiDetailTiket.ID_TIKET])

    var detailTiketUiState: DetailTiketUiState by mutableStateOf(DetailTiketUiState())
        private set

    init {
        getTiketById()
    }

    fun getTiketById() {
        viewModelScope.launch {
            detailTiketUiState = DetailTiketUiState(isLoading = true)
            try {
                val result = tiketRepository.getTiketById(id_tiket)
                detailTiketUiState = DetailTiketUiState(
                    detailTiketUiEvent = result.toDetailTiketUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailTiketUiState = DetailTiketUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
}


data class DetailTiketUiState(
    val detailTiketUiEvent: InsertTiketUiEvent = InsertTiketUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiTiketEmpty: Boolean
        get() = detailTiketUiEvent == InsertTiketUiEvent()

    val isUiTiketNotEmpty: Boolean
        get() = detailTiketUiEvent != InsertTiketUiEvent()
}

fun Tiket.toDetailTiketUiEvent(): InsertTiketUiEvent {
    return InsertTiketUiEvent(
        id_tiket = id_tiket,
        id_event = id_event?:" ",
        id_peserta = id_peserta?:" ",
        nama_peserta = nama_peserta,
        nama_event = nama_event,
        kapasitas_tiket = kapasitas_tiket,
        harga_tiket = harga_tiket,
        tanggal_event = tanggal_event,
        lokasi_event = lokasi_event,
    )
}