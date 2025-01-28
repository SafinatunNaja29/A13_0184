package com.example.finalproject.ui.viewmodel.Transaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Tiket
import com.example.finalproject.model.Transaksi
import com.example.finalproject.repository.TransaksiRepository
import com.example.finalproject.ui.navigation.DestinasiNavigasi
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

    private val _tiketListState = MutableStateFlow<List<Tiket>>(emptyList())
    val tiketListState: StateFlow<List<Tiket>> = _tiketListState

    init{
        viewModelScope.launch {
            updateTransaksiUIState = transaksiRepository.getTransaksiById(_id_transaksi)
                .toUiStateTransaksi()
        }
    }
    fun updateTransaksiState(insertTransaksiUiEvent: InsertTransaksiUiEvent) {
        updateTransaksiUIState = updateTransaksiUIState.copy(insertTransaksiUiEvent = insertTransaksiUiEvent)
    }

    suspend fun updateTransaksi() {
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

fun Transaksi.toUIStateTransaksi(): InsertTransaksiUiState = InsertTransaksiUiState(
    insertTransaksiUiEvent = this.toDetailTransaksiUiEvent(),
)