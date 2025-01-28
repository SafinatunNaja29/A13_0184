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

object DestinasiUpdateEvent: DestinasiNavigasi {
    override val route = "update event"
    const val ID_EVENT = "id_event"
    override val titleRes = "Detail Event"
    val routeWithArg = "$route/{$ID_EVENT}"
}

class UpdateEventViewModel(
    savedStateHandle: SavedStateHandle,
    private val eventRepository: EventRepository
) : ViewModel() {
    var updateEventUIState by mutableStateOf(InsertEventUiState())
        private set
    private val _id_event: String = checkNotNull(savedStateHandle[DestinasiUpdateEvent.ID_EVENT])

    init{
        viewModelScope.launch {
            updateEventUIState = eventRepository.getEventById(_id_event)
                .toUiStateEvent()
        }
    }
    fun updateEventState(insertEventUiEvent: InsertEventUiEvent) {
        updateEventUIState = updateEventUIState.copy(insertEventUiEvent = insertEventUiEvent)
    }

    suspend fun updateEvent() {
        try {
            eventRepository.updateEvent(
                id_event = _id_event,
                event = updateEventUIState.insertEventUiEvent.toEvent()
            )
        } catch (e: Exception) {
            updateEventUIState = updateEventUIState.copy(error = e.message)
        }
    }
}

fun Event.toUIStateEvent(): InsertEventUiState = InsertEventUiState(
    insertEventUiEvent = this.toDetailEventUiEvent(),
)