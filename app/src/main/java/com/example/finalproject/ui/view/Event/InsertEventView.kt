package com.example.finalproject.ui.view.Event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.ui.customwidget.CostumeTopAppBar
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import com.example.finalproject.ui.viewmodel.Event.InsertEventUiEvent
import com.example.finalproject.ui.viewmodel.Event.InsertEventUiState
import com.example.finalproject.ui.viewmodel.Event.InsertEventViewModel
import com.example.finalproject.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEntryEvent: DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Insert Event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryEventScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertEventViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                canNavigateBack = true,
                scrollBehavior = scrollBehaviour,
                navigateUp = navigateBack,
                judul = "Insert Data Event"
            )
        }
    ) { innerPadding ->
        EntryBodyEvent(
            insertEventUiState = viewModel.uiEventState,
            onEventValueChange = viewModel::updateInsertEventState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertEvent()
                    navigateBack()
                }},
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyEvent(
    insertEventUiState: InsertEventUiState,
    onEventValueChange: (InsertEventUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertEventUiEvent = insertEventUiState.insertEventUiEvent,
            onValueChange = onEventValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8B0000),
                contentColor = Color.White)
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormInput(
    insertEventUiEvent: InsertEventUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertEventUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertEventUiEvent.id_event,
            onValueChange = { onValueChange(insertEventUiEvent.copy(id_event = it)) },
            label = { Text("Id Event") },
            placeholder = { Text("Masukkan ID Event") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertEventUiEvent.nama_event,
            onValueChange = { onValueChange(insertEventUiEvent.copy(nama_event = it)) },
            label = { Text(text = "Nama Event") },
            placeholder = { Text("Masukkan Nama Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertEventUiEvent.deskripsi_event,
            onValueChange = { onValueChange(insertEventUiEvent.copy(deskripsi_event = it)) },
            label = { Text(text = "Deskripsi Event") },
            placeholder = { Text("Masukkan Deskripsi Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertEventUiEvent.lokasi_event,
            onValueChange = { onValueChange(insertEventUiEvent.copy(lokasi_event = it)) },
            label = { Text(text = "Lokasi Event") },
            placeholder = { Text("Masukkan Lokasi Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertEventUiEvent.tanggal_event,
            onValueChange = { onValueChange(insertEventUiEvent.copy(tanggal_event = it)) },
            label = { Text(text = "Tanggal Event") },
            placeholder = { Text("Masukkan Tanggal Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

    }
}