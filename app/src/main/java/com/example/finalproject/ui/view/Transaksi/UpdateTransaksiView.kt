package com.example.finalproject.ui.view.Transaksi

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
import com.example.finalproject.ui.viewmodel.Transaksi.UpdateTransaksiViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateTransaksi: DestinasiNavigasi {
    override val route = "update transaksi"
    const val ID_TRANSAKSI = "id_transaksi"
    override val titleRes = "Edit Transaksi"
    val routeWithArg = "$route/{$ID_TRANSAKSI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTransaksiView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateTransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val insertTransaksiUiState = viewModel.updateTransaksiUIState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val tiketList = viewModel.tiketList

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                judul = "Update Data Transaksi"
            )
        }
    ) { innerPadding ->
        EntryBodyTransaksi(
            insertTransaksiUiState = insertTransaksiUiState,
            onTransaksiValueChange = { viewModel.updateTransaksiState(it) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTransaksi()
                    navigateBack()
                }
            },
            tiketList = tiketList,
            modifier = Modifier.padding(innerPadding)
        )
    }
}