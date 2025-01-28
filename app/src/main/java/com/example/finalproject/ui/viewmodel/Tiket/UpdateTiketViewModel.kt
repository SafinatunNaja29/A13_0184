package com.example.finalproject.ui.viewmodel.Tiket

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

    private val _eventListState = MutableStateFlow<List<Event>>(emptyList())
    val eventListState: StateFlow<List<Event>> = _eventListState

    private val _pesertaListState = MutableStateFlow<List<Peserta>>(emptyList())
    val pesertaListState: StateFlow<List<Peserta>> = _pesertaListState

    init{
        viewModelScope.launch {
            updateTiketUIState = tiketRepository.getTiketById(_id_tiket)
                .toUiStateTiket()
        }
    }
    fun updateTiketState(insertTiketUiEvent: InsertTiketUiEvent) {
        updateTiketUIState = updateTiketUIState.copy(insertTiketUiEvent = insertTiketUiEvent)
    }

    suspend fun updateTiket() {
        try {
            tiketRepository.updateTiket(
                id_tiket = _id_tiket,
                tiket = updateTiketUIState.insertTiketUiEvent.toTiket()
            )
        } catch (e: Exception) {
            updateTiketUIState = updateTiketUIState.copy(error = e.message)
        }
    }
}

fun Tiket.toUIStateTiket(): InsertTiketUiState = InsertTiketUiState(
    insertTiketUiEvent = this.toDetailTiketUiEvent(),
)