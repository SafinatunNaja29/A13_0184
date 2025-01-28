package com.example.finalproject.ui.viewmodel.Tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Event
import com.example.finalproject.model.Peserta
import com.example.finalproject.model.Tiket
import com.example.finalproject.repository.EventRepository
import com.example.finalproject.repository.PesertaRepository
import com.example.finalproject.repository.TiketRepository
import kotlinx.coroutines.launch

class InsertTiketViewModel(
    private val tiket: TiketRepository,
    private val eventRepository: EventRepository,
    private val pesertaRepository: PesertaRepository
): ViewModel() {
    // State untuk UI
    var uiTiketState by mutableStateOf(InsertTiketUiState())
        private set

    // List untuk dropdown event dan peserta
    var eventList by mutableStateOf<List<Event>>(emptyList())
        private set

    var pesertaList by mutableStateOf<List<Peserta>>(emptyList())
        private set

    init {
        loadEvents()
        loadPeserta()
    }

    // Fungsi untuk mengambil data event dari repository
    private fun loadEvents() {
        viewModelScope.launch {
            try {
                eventList = eventRepository.getEvent() // Ambil data event
            } catch (e: Exception) {
                // Handle error jika diperlukan
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk mengambil data peserta dari repository
    private fun loadPeserta() {
        viewModelScope.launch {
            try {
                pesertaList = pesertaRepository.getPeserta() // Ambil data peserta
            } catch (e: Exception) {
                // Handle error jika diperlukan
                e.printStackTrace()
            }
        }
    }


    fun updateInsertTiketState(insertTiketUiEvent: InsertTiketUiEvent){
        uiTiketState = InsertTiketUiState(insertTiketUiEvent = insertTiketUiEvent)
    }


    suspend fun insertTiket(){
        viewModelScope.launch {
            try{
                tiket.insertTiket(uiTiketState.insertTiketUiEvent.toTiket())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertTiketUiState(
    val insertTiketUiEvent: InsertTiketUiEvent = InsertTiketUiEvent(),
    val error: String? = null
)

data class InsertTiketUiEvent(
    val id_tiket: String = "",
    val id_event: String = "",
    val id_peserta: String = "",
    val nama_peserta: String = "",
    val nama_event: String = "",
    val kapasitas_tiket: String = "",
    val harga_tiket: String = "",
    val tanggal_event: String = "",
    val lokasi_event: String = "",
)

fun InsertTiketUiEvent.toTiket(): Tiket = Tiket (
    id_tiket = id_tiket,
    id_event = id_event,
    id_peserta = id_peserta,
    nama_peserta = nama_peserta,
    nama_event = nama_event,
    kapasitas_tiket = kapasitas_tiket,
    harga_tiket = harga_tiket,
    tanggal_event = tanggal_event,
    lokasi_event = lokasi_event,
)

fun Tiket.toUiStateTiket(): InsertTiketUiState = InsertTiketUiState(
    insertTiketUiEvent = toInsertTiketUiEvent()
)

fun Tiket.toInsertTiketUiEvent(): InsertTiketUiEvent = InsertTiketUiEvent(
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