package com.example.finalproject.ui.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object DestinasiHomeApp: DestinasiNavigasi{
    override val route  = "homeapp"
    override val titleRes = "Home App"
}

object DestinasiHomeEvent: DestinasiNavigasi{
    override val route  = "homeevent"
    override val titleRes = "Home Event"
}

object DestinasiHomePeserta: DestinasiNavigasi{
    override val route  = "home peserta"
    override val titleRes = "Home Peserta"
}

object DestinasiHomeTiket: DestinasiNavigasi{
    override val route  = "home tiket"
    override val titleRes = "Home Tiket"
}

object DestinasiHomeTransaksi: DestinasiNavigasi{
    override val route  = "home transaksi"
    override val titleRes = "Home Transaksi"
}