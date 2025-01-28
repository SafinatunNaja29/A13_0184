package com.example.finalproject.ui.viewmodel.Peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Peserta
import com.example.finalproject.repository.PesertaRepository
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiUpdatePeserta: DestinasiNavigasi {
    override val route = "update peserta"
    const val ID_PESERTA = "id_peserta"
    override val titleRes = "Detail Peserta"
    val routeWithArg = "$route/{$ID_PESERTA}"
}

class UpdatePesertaViewModel(
    savedStateHandle: SavedStateHandle,
    private val pesertaRepository: PesertaRepository
) : ViewModel() {
    var updatePesertaUIState by mutableStateOf(InsertPesertaUiState())
        private set
    private val _id_peserta: String = checkNotNull(savedStateHandle[DestinasiUpdatePeserta.ID_PESERTA])

    init{
        viewModelScope.launch {
            updatePesertaUIState = pesertaRepository.getPesertaById(_id_peserta)
                .toUiStatePeserta()
        }
    }
    fun updatePesertaState(insertPesertaUiEvent: InsertPesertaUiEvent) {
        updatePesertaUIState = updatePesertaUIState.copy(insertPesertaUiEvent = insertPesertaUiEvent)
    }

    suspend fun updatePeserta() {
        try {
            pesertaRepository.updatePeserta(
                id_peserta = _id_peserta,
                peserta = updatePesertaUIState.insertPesertaUiEvent.toPeserta()
            )
        } catch (e: Exception) {
            updatePesertaUIState = updatePesertaUIState.copy(error = e.message)
        }
    }
}

fun Peserta.toUIStatePeserta(): InsertPesertaUiState = InsertPesertaUiState(
    insertPesertaUiEvent = this.toDetailPesertaUiEvent(),
)