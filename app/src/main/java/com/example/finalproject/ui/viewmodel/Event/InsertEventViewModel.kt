package com.example.finalproject.ui.viewmodel.Event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Event
import com.example.finalproject.repository.EventRepository
import kotlinx.coroutines.launch

class InsertEventViewModel(private val event: EventRepository): ViewModel() {
    var uiEventState by mutableStateOf(InsertEventUiState())
        private set

    fun updateInsertEventState(insertEventUiEvent: InsertEventUiEvent){
        uiEventState = InsertEventUiState(insertEventUiEvent = insertEventUiEvent)
    }

    suspend fun insertEvent(){
        viewModelScope.launch {
            try{
                event.insertEvent(uiEventState.insertEventUiEvent.toEvent())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertEventUiState(
    val insertEventUiEvent: InsertEventUiEvent = InsertEventUiEvent(),
    val error: String? = null
)

data class InsertEventUiEvent(
    val id_event: String = "",
    val nama_event: String = "",
    val deskripsi_event: String = "",
    val lokasi_event: String = "",
    val tanggal_event: String = "",
)

fun InsertEventUiEvent.toEvent(): Event = Event (
    id_event = id_event,
    nama_event = nama_event,
    deskripsi_event = deskripsi_event,
    lokasi_event = lokasi_event,
    tanggal_event = tanggal_event
)

fun Event.toUiStateEvent(): InsertEventUiState = InsertEventUiState(
    insertEventUiEvent = toInsertEventUiEvent()
)

fun Event.toInsertEventUiEvent(): InsertEventUiEvent = InsertEventUiEvent(
    id_event = id_event,
    nama_event = nama_event,
    deskripsi_event = deskripsi_event,
    lokasi_event = lokasi_event,
    tanggal_event = tanggal_event
)