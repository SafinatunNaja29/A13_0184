package com.example.finalproject.ui.viewmodel.Peserta

import com.example.finalproject.model.Peserta

sealed class HomePesertaUiState{
    data class Success(val peserta: List<Peserta>): HomePesertaUiState()
    object Loading: HomePesertaUiState()
    object Error: HomePesertaUiState()
}