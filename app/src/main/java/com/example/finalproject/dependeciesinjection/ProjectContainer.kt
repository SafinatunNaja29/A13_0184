package com.example.finalproject.dependeciesinjection

import com.example.finalproject.repository.EventRepository
import com.example.finalproject.repository.NetworkEventRepository
import com.example.finalproject.repository.NetworkPesertaRepository
import com.example.finalproject.repository.NetworkTiketRepository
import com.example.finalproject.repository.NetworkTransaksiRepository
import com.example.finalproject.repository.PesertaRepository
import com.example.finalproject.repository.TiketRepository
import com.example.finalproject.repository.TransaksiRepository
import com.example.finalproject.service.EventService
import com.example.finalproject.service.PesertaService
import com.example.finalproject.service.TiketService
import com.example.finalproject.service.TransaksiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val eventRepository: EventRepository
    val pesertaRepository : PesertaRepository
    val tiketRepository : TiketRepository
    val transaksiRepository : TransaksiRepository
}

class ProjectContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:80/umyTI/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()


    private val eventService : EventService by lazy {
        retrofit.create(EventService::class.java)
    }

    override val eventRepository: EventRepository by lazy {
        NetworkEventRepository(eventService)
    }

    private val pesertaService : PesertaService by lazy {
        retrofit.create(PesertaService::class.java)
    }

    override val pesertaRepository: PesertaRepository by lazy {
        NetworkPesertaRepository(pesertaService)
    }

    private val tiketService : TiketService by lazy {
        retrofit.create(TiketService::class.java)
    }

    override val tiketRepository: TiketRepository by lazy {
        NetworkTiketRepository(tiketService)
    }

    private val transaksiService : TransaksiService by lazy {
        retrofit.create(TransaksiService::class.java)
    }

    override val transaksiRepository: TransaksiRepository by lazy {
        NetworkTransaksiRepository(transaksiService)
    }
}