package com.example.finalproject.ui.viewmodel.Transaksi

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Tiket
import com.example.finalproject.model.Transaksi
import com.example.finalproject.repository.TiketRepository
import com.example.finalproject.repository.TransaksiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class InsertTransaksiViewModel(
    private val transaksi: TransaksiRepository,
    private val tiket: TiketRepository,
): ViewModel() {
    var uiTransaksiState by mutableStateOf(InsertTransaksiUiState())
        private set

    val tiketList = MutableStateFlow<List<Tiket>>(emptyList())

    init {
        loadTiket()
    }

    private fun loadTiket() {
        viewModelScope.launch {
            try {
                tiketList.value = tiket.getTiket()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertTransaksiState(insertTransaksiUiEvent: InsertTransaksiUiEvent){
        uiTransaksiState = InsertTransaksiUiState(insertTransaksiUiEvent = insertTransaksiUiEvent)
    }

    suspend fun insertTransaksi(){
        Log.d("InsertTransaksi", uiTransaksiState.insertTransaksiUiEvent.toString())
        viewModelScope.launch {
            try{
                transaksi.insertTransaksi(uiTransaksiState.insertTransaksiUiEvent.toTransaksi())
            }catch (e: Exception){
                Log.e("InsertTransaksi", "Error: ${e.message}", e)
            }
        }
    }
}

data class InsertTransaksiUiState(
    val insertTransaksiUiEvent: InsertTransaksiUiEvent = InsertTransaksiUiEvent(),
    val error: String? = null
)

data class InsertTransaksiUiEvent(
    val id_transaksi: String = "",
    val id_tiket: String = "",
    val nama_event: String = "",
    val nama_peserta: String = "",
    val jumlah_tiket: String = "",
    val jumlah_pembayaran: String = "",
    val tanggal_transaksi: String = ""
)

fun InsertTransaksiUiEvent.toTransaksi(): Transaksi = Transaksi (
    id_transaksi = id_transaksi,
    id_tiket = id_tiket,
    nama_event = nama_event,
    nama_peserta = nama_peserta,
    jumlah_tiket = jumlah_tiket,
    jumlah_pembayaran = jumlah_pembayaran,
    tanggal_transaksi = tanggal_transaksi
)

fun Transaksi.toUiStateTransaksi(): InsertTransaksiUiState = InsertTransaksiUiState(
    insertTransaksiUiEvent = toInsertTransaksiUiEvent()
)

fun Transaksi.toInsertTransaksiUiEvent(): InsertTransaksiUiEvent = InsertTransaksiUiEvent(
    id_transaksi = id_transaksi,
    id_tiket = id_tiket?:" ",
    nama_event = nama_event,
    nama_peserta = nama_peserta,
    jumlah_tiket = jumlah_tiket,
    jumlah_pembayaran = jumlah_pembayaran,
    tanggal_transaksi = tanggal_transaksi
)