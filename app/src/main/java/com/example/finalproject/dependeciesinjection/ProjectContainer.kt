package com.example.finalproject.dependeciesinjection

import com.example.finalproject.repository.PesertaRepository

interface AppContainer{
    val eventRepository: EventRepository
    val pesertaRepository : PesertaRepository
    val tiketRepository : TiketRepository
    val transaksiRepository : TransaksiRepository
}