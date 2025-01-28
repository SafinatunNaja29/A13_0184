package com.example.finalproject.ui.view.Peserta

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.ui.viewmodel.Peserta.UpdatePesertaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePesertaView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePesertaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val insertPesertaUiState = viewModel.updatePesertaUIState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                judul = "Update Data Peserta"
            )
        }
    ) { innerPadding ->
        EntryBodyPeserta(
            insertPesertaUiState = insertPesertaUiState,
            onPesertaValueChange = { viewModel.updatePesertaState(it) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePeserta()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}