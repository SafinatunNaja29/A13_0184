package com.example.finalproject.ui.view.Tiket

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.ui.customwidget.CostumeTopAppBar
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import com.example.finalproject.ui.viewmodel.PenyediaViewModel
import com.example.finalproject.ui.viewmodel.Tiket.UpdateTiketViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateTiket: DestinasiNavigasi {
    override val route = "update tiket"
    const val ID_TIKET = "id_tiket"
    override val titleRes = "Edit Tiket"
    val routeWithArg = "$route/{$ID_TIKET}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTiketView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val insertTiketUiState = viewModel.updateTiketUIState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val eventList = viewModel.eventList
    val pesertaList = viewModel.pesertaList

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                judul = "Update Data Tiket"
            )
        }
    ) { innerPadding ->
        EntryBodyTiket(
            insertTiketUiState = insertTiketUiState,
            onTiketValueChange = { viewModel.updateTiketState(it) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTiket()
                    navigateBack()
                }
            },
            eventList = eventList,
            pesertaList = pesertaList,
            modifier = Modifier.padding(innerPadding)
        )
    }
}