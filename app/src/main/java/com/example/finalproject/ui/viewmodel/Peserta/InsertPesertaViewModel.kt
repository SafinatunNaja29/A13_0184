package com.example.finalproject.ui.viewmodel.Peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Peserta
import com.example.finalproject.repository.PesertaRepository
import kotlinx.coroutines.launch

class InsertPesertaViewModel(private val peserta: PesertaRepository): ViewModel() {
    var uiPesertaState by mutableStateOf(InsertPesertaUiState())
        private set

    fun updateInsertPesertaState(insertPesertaUiEvent: InsertPesertaUiEvent){
        uiPesertaState = InsertPesertaUiState(insertPesertaUiEvent = insertPesertaUiEvent)
    }

    suspend fun insertPeserta(){
        viewModelScope.launch {
            try{
                peserta.insertPeserta(uiPesertaState.insertPesertaUiEvent.toPeserta())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertPesertaUiState(
    val insertPesertaUiEvent: InsertPesertaUiEvent = InsertPesertaUiEvent(),
    val error: String? = null
)
data class InsertPesertaUiEvent(
    val id_peserta: String = "",
    val nama_peserta: String = "",
    val email: String = "",
    val nomor_telepon: String = "",
)

fun InsertPesertaUiEvent.toPeserta(): Peserta = Peserta (
    id_peserta = id_peserta,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon
)

fun Peserta.toUiStatePeserta(): InsertPesertaUiState = InsertPesertaUiState(
    insertPesertaUiEvent = toInsertPesertaUiEvent()
)

fun Peserta.toInsertPesertaUiEvent(): InsertPesertaUiEvent = InsertPesertaUiEvent(
    id_peserta = id_peserta,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon
)