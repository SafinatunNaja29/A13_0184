package com.example.finalproject.ui.viewmodel.Event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Event
import com.example.finalproject.repository.EventRepository
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiDetailEvent: DestinasiNavigasi {
    override val route = "detail event"
    const val ID_EVENT = "id_event"
    override val titleRes = "Detail Event"
    val routeWithArg = "$route/{$ID_EVENT}"
}

class DetailEventViewModel(
    savedStateHandle: SavedStateHandle,
    private val eventRepository: EventRepository
) : ViewModel() {
    val id_event: String = checkNotNull(savedStateHandle[DestinasiDetailEvent.ID_EVENT])

    var detailEventUiState: DetailEventUiState by mutableStateOf(DetailEventUiState())
        private set

    init {
        getEventById()
    }

    fun getEventById() {
        viewModelScope.launch {
            detailEventUiState = DetailEventUiState(isLoading = true)
            try {
                val result = eventRepository.getEventById(id_event)
                detailEventUiState = DetailEventUiState(
                    detailEventUiEvent = result.toDetailEventUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailEventUiState = DetailEventUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
}


data class DetailEventUiState(
    val detailEventUiEvent: InsertEventUiEvent = InsertEventUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailEventUiEvent == InsertEventUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailEventUiEvent != InsertEventUiEvent()
}

fun Event.toDetailEventUiEvent(): InsertEventUiEvent {
    return InsertEventUiEvent(
        id_event = id_event,
        nama_event = nama_event,
        deskripsi_event = deskripsi_event,
        lokasi_event = lokasi_event,
        tanggal_event = tanggal_event
    )
}