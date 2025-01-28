package com.example.finalproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalproject.HomeApp
import com.example.finalproject.ui.view.Event.DestinasiEntryEvent
import com.example.finalproject.ui.view.Event.DestinasiUpdateEvent
import com.example.finalproject.ui.view.Event.DetailEventView
import com.example.finalproject.ui.view.Event.EntryEventScreen
import com.example.finalproject.ui.view.Event.HomeEventScreen
import com.example.finalproject.ui.view.Event.UpdateEventView
import com.example.finalproject.ui.view.Peserta.DestinasiEntryPeserta
import com.example.finalproject.ui.view.Peserta.DestinasiUpdatePeserta
import com.example.finalproject.ui.view.Peserta.DetailPesertaView
import com.example.finalproject.ui.view.Peserta.EntryPesertaScreen
import com.example.finalproject.ui.view.Peserta.HomePesertaScreen
import com.example.finalproject.ui.view.Peserta.UpdatePesertaView
import com.example.finalproject.ui.view.Tiket.DestinasiEntryTiket
import com.example.finalproject.ui.view.Tiket.DestinasiUpdateTiket
import com.example.finalproject.ui.view.Tiket.DetailTiketView
import com.example.finalproject.ui.view.Tiket.EntryTiketScreen
import com.example.finalproject.ui.view.Tiket.HomeTiketScreen
import com.example.finalproject.ui.view.Tiket.UpdateTiketView
import com.example.finalproject.ui.view.Transaksi.DestinasiEntryTransaksi
import com.example.finalproject.ui.view.Transaksi.DestinasiUpdateTransaksi
import com.example.finalproject.ui.view.Transaksi.DetailTransaksiView
import com.example.finalproject.ui.view.Transaksi.EntryTransaksiScreen
import com.example.finalproject.ui.view.Transaksi.HomeTransaksiScreen
import com.example.finalproject.ui.view.Transaksi.UpdateTransaksiView
import com.example.finalproject.ui.viewmodel.Event.DestinasiDetailEvent
import com.example.finalproject.ui.viewmodel.Peserta.DestinasiDetailPeserta
import com.example.finalproject.ui.viewmodel.Tiket.DestinasiDetailTiket
import com.example.finalproject.ui.viewmodel.Transaksi.DestinasiDetailTransaksi

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier : Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeApp.route

    ){
        composable(route = DestinasiHomeApp.route){
            HomeApp(
                onHalamanHomeEvent = {
                    navController.navigate(DestinasiHomeEvent.route)
                },
                onHalamanHomePeserta = {
                    navController.navigate(DestinasiHomePeserta.route)
                },
                onHalamanHomeTiket = {
                    navController.navigate(DestinasiHomeTiket.route)
                },
                onHalamanHomeTransaksi = {
                    navController.navigate(DestinasiHomeTransaksi.route)
                },
                modifier = modifier
            )
        }

        composable(route = DestinasiHomeEvent.route){
            HomeEventScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntryEvent.route)},
                onDetailClick = { id_event ->
                    navController.navigate("${DestinasiDetailEvent.route}/$id_event")
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeApp.route) {
                        popUpTo(DestinasiHomeApp.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = DestinasiHomePeserta.route){
            HomePesertaScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntryPeserta.route)},
                onDetailClick = { id_peserta ->
                    navController.navigate("${DestinasiDetailPeserta.route}/$id_peserta")
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeApp.route) {
                        popUpTo(DestinasiHomeApp.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = DestinasiHomeTiket.route){
            HomeTiketScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntryTiket.route)},
                onDetailClick = { id_tiket ->
                    navController.navigate("${DestinasiDetailTiket.route}/$id_tiket")
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeApp.route) {
                        popUpTo(DestinasiHomeApp.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = DestinasiHomeTransaksi.route){
            HomeTransaksiScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntryTransaksi.route)},
                onDetailClick = { id_transaksi ->
                    navController.navigate("${DestinasiDetailTransaksi.route}/$id_transaksi")
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeApp.route) {
                        popUpTo(DestinasiHomeApp.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = DestinasiEntryEvent.route){
            EntryEventScreen(navigateBack = {
                navController.navigate(DestinasiHomeEvent.route){
                    popUpTo(DestinasiHomeEvent.route){
                        inclusive = true
                    }
                }
            })
        }

        composable(route = DestinasiEntryPeserta.route){
            EntryPesertaScreen(navigateBack = {
                navController.navigate(DestinasiHomePeserta.route){
                    popUpTo(DestinasiHomePeserta.route){
                        inclusive = true
                    }
                }
            })
        }

        composable(route = DestinasiEntryTiket.route){
            EntryTiketScreen(navigateBack = {
                navController.navigate(DestinasiHomeTiket.route){
                    popUpTo(DestinasiHomeTiket.route){
                        inclusive = true
                    }
                }
            })
        }

        composable(route = DestinasiEntryTransaksi.route){
            EntryTransaksiScreen(navigateBack = {
                navController.navigate(DestinasiHomeTransaksi.route){
                    popUpTo(DestinasiHomeTransaksi.route){
                        inclusive = true
                    }
                }
            })
        }

        composable(
            DestinasiDetailEvent.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailEvent.ID_EVENT){
                    type = NavType.StringType
                }
            )
        ) {
            val id_event = it.arguments?.getString(DestinasiDetailEvent.ID_EVENT)
            id_event?.let {
                DetailEventView(
                    navigateBack = {
                        navController.navigate(DestinasiHomeEvent.route) {
                            popUpTo(DestinasiHomeEvent.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToEdit =  {
                        navController.navigate("${DestinasiUpdateEvent.route}/$it")
                    }
                )
            }
        }

        composable(
            DestinasiDetailPeserta.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailPeserta.ID_PESERTA){
                    type = NavType.StringType
                }
            )
        ) {
            val id_peserta = it.arguments?.getString(DestinasiDetailPeserta.ID_PESERTA)
            id_peserta?.let {
                DetailPesertaView(
                    navigateBack = {
                        navController.navigate(DestinasiHomePeserta.route) {
                            popUpTo(DestinasiHomePeserta.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToEdit =  {
                        navController.navigate("${DestinasiUpdatePeserta.route}/$it")
                    }
                )
            }
        }

        composable(
            DestinasiDetailTiket.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailTiket.ID_TIKET){
                    type = NavType.StringType
                }
            )
        ) {
            val id_tiket = it.arguments?.getString(DestinasiDetailTiket.ID_TIKET)
            id_tiket?.let {
                DetailTiketView(
                    navigateBack = {
                        navController.navigate(DestinasiHomeTiket.route) {
                            popUpTo(DestinasiHomeTiket.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToEdit =  {
                        navController.navigate("${DestinasiUpdateTiket.route}/$it")
                    }
                )
            }
        }

        composable(
            DestinasiDetailTransaksi.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailTransaksi.ID_TRANSAKSI){
                    type = NavType.StringType
                }
            )
        ) {
            val id_transaksi = it.arguments?.getString(DestinasiDetailTransaksi.ID_TRANSAKSI)
            id_transaksi?.let {
                DetailTransaksiView(
                    navigateBack = {
                        navController.navigate(DestinasiHomeTransaksi.route) {
                            popUpTo(DestinasiHomeTransaksi.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToEdit =  {
                        navController.navigate("${DestinasiUpdateTransaksi.route}/$it")
                    }
                )
            }
        }

        composable(
            DestinasiUpdateEvent.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateEvent.ID_EVENT){
                    type = NavType.StringType
                }
            )
        ){
            val id_event = it.arguments?.getString(DestinasiUpdateEvent.ID_EVENT)
            id_event?.let {
                UpdateEventView(
                    navigateBack = {
                        navController.navigate("${DestinasiDetailEvent.route}/$it") {
                            popUpTo("${DestinasiDetailEvent.route}/$it") {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }

        composable(
            DestinasiUpdatePeserta.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdatePeserta.ID_PESERTA){
                    type = NavType.StringType
                }
            )
        ){
            val id_peserta = it.arguments?.getString(DestinasiUpdatePeserta.ID_PESERTA)
            id_peserta?.let {
                UpdatePesertaView(
                    navigateBack = {
                        navController.navigate("${DestinasiDetailPeserta.route}/$it") {
                            popUpTo("${DestinasiDetailPeserta.route}/$it") {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }

        composable(
            DestinasiUpdateTiket.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateTiket.ID_TIKET){
                    type = NavType.StringType
                }
            )
        ){
            val id_tiket = it.arguments?.getString(DestinasiUpdateTiket.ID_TIKET)
            id_tiket?.let {
                UpdateTiketView(
                    navigateBack = {
                        navController.navigate("${DestinasiDetailTiket.route}/$it") {
                            popUpTo("${DestinasiDetailTiket.route}/$it") {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }

        composable(
            DestinasiUpdateTransaksi.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateTransaksi.ID_TRANSAKSI){
                    type = NavType.StringType
                }
            )
        ){
            val id_transaksi = it.arguments?.getString(DestinasiUpdateTransaksi.ID_TRANSAKSI)
            id_transaksi?.let {
                UpdateTransaksiView(
                    navigateBack = {
                        navController.navigate("${DestinasiDetailTransaksi.route}/$it") {
                            popUpTo("${DestinasiDetailTransaksi.route}/$it") {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }
    }
}