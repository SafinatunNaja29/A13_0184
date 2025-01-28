package com.example.finalproject.ui.viewmodel.Transaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Transaksi
import com.example.finalproject.repository.TransaksiRepository
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiDetailTransaksi: DestinasiNavigasi {
    override val route = "detail transaksi"
    const val ID_TRANSAKSI = "id_transaksi"
    override val titleRes = "Detail Transaksi"
    val routeWithArg = "$route/{$ID_TRANSAKSI}"
}

class DetailTransaksiViewModel(
    savedStateHandle: SavedStateHandle,
    private val transaksiRepository: TransaksiRepository
) : ViewModel() {
    val id_transaksi: String = checkNotNull(savedStateHandle[DestinasiDetailTransaksi.ID_TRANSAKSI])

    var detailTransaksiUiState: DetailTransaksiUiState by mutableStateOf(DetailTransaksiUiState())
        private set

    init {
        getTransaksiById()
    }

    fun getTransaksiById() {
        viewModelScope.launch {
            detailTransaksiUiState = DetailTransaksiUiState(isLoading = true)
            try {
                val result = transaksiRepository.getTransaksiById(id_transaksi)
                detailTransaksiUiState = DetailTransaksiUiState(
                    detailTransaksiUiEvent = result.toDetailTransaksiUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailTransaksiUiState = DetailTransaksiUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
}


data class DetailTransaksiUiState(
    val detailTransaksiUiEvent: InsertTransaksiUiEvent = InsertTransaksiUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiTransaksiEmpty: Boolean
        get() = detailTransaksiUiEvent == InsertTransaksiUiEvent()

    val isUiTransaksiNotEmpty: Boolean
        get() = detailTransaksiUiEvent != InsertTransaksiUiEvent()
}

fun Transaksi.toDetailTransaksiUiEvent(): InsertTransaksiUiEvent {
    return InsertTransaksiUiEvent(

        id_transaksi = id_transaksi,
        id_tiket = id_tiket?:" ",
        nama_event = nama_event,
        nama_peserta = nama_peserta,
        jumlah_tiket = jumlah_tiket,
        jumlah_pembayaran = jumlah_pembayaran,
        tanggal_transaksi = tanggal_transaksi
    )
}