package com.example.finalproject.ui.viewmodel.Transaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalproject.model.Transaksi
import com.example.finalproject.repository.TransaksiRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeTransaksiUiState{
    data class Success(val transaksi: List<Transaksi>): HomeTransaksiUiState()
    object Loading: HomeTransaksiUiState()
    object Error: HomeTransaksiUiState()
}

class HomeTransaksiViewModel(private val transaksi: TransaksiRepository): ViewModel() {
    var transaksiUiState: HomeTransaksiUiState by mutableStateOf(HomeTransaksiUiState.Loading)
        private set

    init {
        getTransaksi()
    }

    fun getTransaksi(){
        viewModelScope.launch {
            transaksiUiState = HomeTransaksiUiState.Loading
            transaksiUiState = try{
                HomeTransaksiUiState.Success(transaksi.getTransaksi())
            }catch (e: IOException){
                HomeTransaksiUiState.Error
            }catch (e: HttpException){
                HomeTransaksiUiState.Error
            }
        }
    }

    fun deleteTransaksi(id_transaksi: String){
        viewModelScope.launch {
            try{
                transaksi.deleteTransaksi(id_transaksi)
            }catch (e: IOException){
                HomeTransaksiUiState.Error
            }catch (e: HttpException){
                HomeTransaksiUiState.Error
            }
        }
    }
}