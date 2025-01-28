package com.example.finalproject.ui.viewmodel.Event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalproject.model.Event
import com.example.finalproject.repository.EventRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeEventUiState{
    data class Success(val event: List<Event>): HomeEventUiState()
    object Loading: HomeEventUiState()
    object Error: HomeEventUiState()
}

class HomeEventViewModel(private val event: EventRepository): ViewModel() {
    var eventUiState: HomeEventUiState by mutableStateOf(HomeEventUiState.Loading)
        private set

    init {
        getEvent()
    }

    fun getEvent(){
        viewModelScope.launch {
            eventUiState = HomeEventUiState.Loading
            eventUiState = try{
                HomeEventUiState.Success(event.getEvent())
            }catch (e: IOException){
                HomeEventUiState.Error
            }catch (e: HttpException){
                HomeEventUiState.Error
            }
        }
    }

    fun deleteEvent(id_event: String){
        viewModelScope.launch {
            try{
                event.deleteEvent(id_event)
            }catch (e: IOException){
                HomeEventUiState.Error
            }catch (e: HttpException){
                HomeEventUiState.Error
            }
        }
    }
}