package com.example.finalproject.ui.viewmodel.Tiket

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
import com.example.finalproject.repository.TiketRepository
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object DestinasiUpdateTiket: DestinasiNavigasi {
    override val route = "update tiket"
    const val ID_TIKET = "id_tiket"
    override val titleRes = "Detail Tiket"
    val routeWithArg = "$route/{$ID_TIKET}"
}

class UpdateTiketViewModel(
    savedStateHandle: SavedStateHandle,
    private val tiketRepository: TiketRepository
) : ViewModel() {
    var updateTiketUIState by mutableStateOf(InsertTiketUiState())
        private set
    private val _id_tiket: String = checkNotNull(savedStateHandle[DestinasiUpdateTiket.ID_TIKET])

    var eventList by mutableStateOf(listOf<Event>())
        private set

    var pesertaList by mutableStateOf(listOf<Peserta>())
        private set
    init {
        fetchDropdownData()
        loadTiketData()
    }

    private fun fetchDropdownData() {
        viewModelScope.launch {
            try {
                eventList = tiketRepository.getTiket().map { tiket ->
                    Event(
                        id_event = tiket.id_event ?: "",
                        nama_event = tiket.nama_event,
                        tanggal_event = tiket.tanggal_event,
                        lokasi_event = tiket.lokasi_event,
                        deskripsi_event = " "
                    )
                }.distinctBy { it.id_event }
                Log.d("DropdownData", "Event List: $eventList")

                pesertaList = tiketRepository.getTiket().map { tiket ->
                    Peserta(
                        id_peserta = tiket.id_peserta?:" ",
                        nama_peserta = tiket.nama_peserta,
                        email = "",
                        nomor_telepon = ""
                    )
                }.distinctBy { it.id_peserta }
                Log.d("DropdownData", "Peserta List: $pesertaList")
            } catch (e: Exception) {
                Log.e("DropdownData", "Error fetching dropdown data: ${e.message}")
            }
        }
    }

    private fun loadTiketData() {
        viewModelScope.launch {
            try {
                val tiket = tiketRepository.getTiketById(_id_tiket)
                updateTiketUIState = tiket.toUIStateTiket()
                Log.d("TiketData", "Tiket loaded: $tiket")
            } catch (e: Exception) {
                Log.e("TiketData", "Error loading tiket data: ${e.message}")
            }
        }
    }
    fun updateTiketState(insertTiketUiEvent: InsertTiketUiEvent) {
        updateTiketUIState = updateTiketUIState.copy(insertTiketUiEvent = insertTiketUiEvent)
    }

    suspend fun updateTiket() {
        viewModelScope.launch {
            try {
                Log.d("UpdateTiket", "Updating tiket with data: ${updateTiketUIState.insertTiketUiEvent.toTiket()}")
                tiketRepository.updateTiket(
                    id_tiket = _id_tiket,
                    tiket = updateTiketUIState.insertTiketUiEvent.toTiket()
                )
                Log.d("UpdateTiket", "Tiket successfully updated")
            } catch (e: Exception) {
                Log.e("UpdateTiket", "Error updating tiket: ${e.message}")
                updateTiketUIState = updateTiketUIState.copy(error = e.message)
            }
        }
    }
}

fun Tiket.toUIStateTiket(): InsertTiketUiState = InsertTiketUiState(
    insertTiketUiEvent = this.toDetailTiketUiEvent(),
)