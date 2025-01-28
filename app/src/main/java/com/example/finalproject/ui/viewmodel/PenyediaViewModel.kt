package com.example.finalproject.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject.ProjectApplication
import com.example.finalproject.ui.viewmodel.Peserta.DetailPesertaViewModel
import com.example.finalproject.ui.viewmodel.Peserta.HomePesertaViewModel
import com.example.finalproject.ui.viewmodel.Peserta.InsertPesertaViewModel
import com.example.finalproject.ui.viewmodel.Peserta.UpdatePesertaViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            HomeEventViewModel(
                ProjectApplication().container.eventRepository)
        }
        initializer {
            InsertEventViewModel(
                ProjectApplication().container.eventRepository)
        }
        initializer {
            DetailEventViewModel(
                createSavedStateHandle(),
                ProjectApplication().container.eventRepository)
        }
        initializer {
            UpdateEventViewModel(
                createSavedStateHandle(),
                ProjectApplication().container.eventRepository)
        }


        initializer {
            HomePesertaViewModel(
                ProjectApplication().container.pesertaRepository)
        }
        initializer {
            InsertPesertaViewModel(
                ProjectApplication().container.pesertaRepository)
        }
        initializer {
            DetailPesertaViewModel(
                createSavedStateHandle(),
                ProjectApplication().container.pesertaRepository)
        }
        initializer {
            UpdatePesertaViewModel(
                createSavedStateHandle(),
                ProjectApplication().container.pesertaRepository)
        }


        initializer {
            HomeTiketViewModel(
                ProjectApplication().container.tiketRepository)
        }
        initializer {
            InsertTiketViewModel(
                ProjectApplication().container.tiketRepository,
                ProjectApplication().container.eventRepository,
                ProjectApplication().container.pesertaRepository
            )
        }
        initializer {
            DetailTiketViewModel(
                createSavedStateHandle(),
                ProjectApplication().container.tiketRepository)
        }
        initializer {
            UpdateTiketViewModel(
                createSavedStateHandle(),
                ProjectApplication().container.tiketRepository)
        }


        initializer {
            HomeTransaksiViewModel(
                ProjectApplication().container.transaksiRepository)
        }
        initializer {
            InsertTransaksiViewModel(
                ProjectApplication().container.transaksiRepository,
                ProjectApplication().container.tiketRepository)
        }
        initializer {
            DetailTransaksiViewModel(
                createSavedStateHandle(),
                ProjectApplication().container.transaksiRepository)
        }
        initializer {
            UpdateTransaksiViewModel(
                createSavedStateHandle(),
                ProjectApplication().container.transaksiRepository)
        }
    }
}

fun CreationExtras.ProjectApplication(): ProjectApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ProjectApplication)