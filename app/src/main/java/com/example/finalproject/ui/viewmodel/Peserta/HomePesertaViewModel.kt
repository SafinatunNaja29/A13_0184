package com.example.finalproject.ui.viewmodel.Peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalproject.model.Peserta
import com.example.finalproject.repository.PesertaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePesertaUiState{
    data class Success(val peserta: List<Peserta>): HomePesertaUiState()
    object Loading: HomePesertaUiState()
    object Error: HomePesertaUiState()
}

class HomePesertaViewModel(private val peserta: PesertaRepository): ViewModel() {
    var pesertaUiState: HomePesertaUiState by mutableStateOf(HomePesertaUiState.Loading)
        private set

    init {
        getPeserta()
    }

    fun getPeserta(){
        viewModelScope.launch {
            pesertaUiState = HomePesertaUiState.Loading
            pesertaUiState = try{
                HomePesertaUiState.Success(peserta.getPeserta())
            }catch (e: IOException){
                HomePesertaUiState.Error
            }catch (e: HttpException){
                HomePesertaUiState.Error
            }
        }
    }

    fun deletePeserta(id_peserta: String){
        viewModelScope.launch {
            try{
                peserta.deletePeserta(id_peserta)
            }catch (e: IOException){
                HomePesertaUiState.Error
            }catch (e: HttpException){
                HomePesertaUiState.Error
            }
        }
    }
}