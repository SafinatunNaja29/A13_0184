package com.example.finalproject.ui.viewmodel.Transaksi

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Event
import com.example.finalproject.model.Peserta
import com.example.finalproject.model.Tiket
import com.example.finalproject.model.Transaksi
import com.example.finalproject.repository.TransaksiRepository
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import com.example.finalproject.ui.viewmodel.Tiket.toTiket
import com.example.finalproject.ui.viewmodel.Tiket.toUIStateTiket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object DestinasiUpdateTransaksi: DestinasiNavigasi {
    override val route = "update transaksi"
    const val ID_TRANSAKSI = "id_transaksi"
    override val titleRes = "Detail Transaksi"
    val routeWithArg = "$route/{$ID_TRANSAKSI}"
}

class UpdateTransaksiViewModel(
    savedStateHandle: SavedStateHandle,
    private val transaksiRepository: TransaksiRepository
) : ViewModel() {
    var updateTransaksiUIState by mutableStateOf(InsertTransaksiUiState())
        private set
    private val _id_transaksi: String = checkNotNull(savedStateHandle[DestinasiUpdateTransaksi.ID_TRANSAKSI])

    private val _tiketList = MutableStateFlow<List<Tiket>>(emptyList())
    val tiketList: StateFlow<List<Tiket>> get() = _tiketList

    init {
        fetchDropdownData()
        loadTransaksiData()
    }

    private fun fetchDropdownData() {
        viewModelScope.launch {
            try {
                val tiketData = transaksiRepository.getTransaksi().map { transaksi ->
                    Tiket(
                        id_tiket = transaksi.id_tiket ?: "",
                        id_event = " ",
                        id_peserta = " ",
                        nama_peserta = transaksi.nama_peserta,
                        nama_event = transaksi.nama_event,
                        kapasitas_tiket = " ",
                        harga_tiket = " ",
                        tanggal_event = " ",
                        lokasi_event = " ",
                    )
                }.distinctBy { it.id_tiket }
                _tiketList.value = tiketData // Update StateFlow
            } catch (e: Exception) {
                Log.e("DropdownData", "Error fetching dropdown data: ${e.message}")
            }
        }
    }

    private fun loadTransaksiData() {
        viewModelScope.launch {
            try {
                val transaksi = transaksiRepository.getTransaksiById(_id_transaksi)
                updateTransaksiUIState = transaksi.toUIStateTransaksi()
            } catch (e: Exception) {
                Log.e("TransaksiData", "Error loading transaksi data: ${e.message}")
            }
        }
    }

    fun updateTransaksiState(insertTransaksiUiEvent: InsertTransaksiUiEvent) {
        updateTransaksiUIState = updateTransaksiUIState.copy(insertTransaksiUiEvent = insertTransaksiUiEvent)
    }

    suspend fun updateTransaksi() {
        viewModelScope.launch {
            try {
                transaksiRepository.updateTransaksi(
                    id_transaksi = _id_transaksi,
                    transaksi = updateTransaksiUIState.insertTransaksiUiEvent.toTransaksi()
                )
            } catch (e: Exception) {
                updateTransaksiUIState = updateTransaksiUIState.copy(error = e.message)
            }
        }
    }
}

fun Transaksi.toUIStateTransaksi(): InsertTransaksiUiState = InsertTransaksiUiState(
    insertTransaksiUiEvent = this.toDetailTransaksiUiEvent(),
)