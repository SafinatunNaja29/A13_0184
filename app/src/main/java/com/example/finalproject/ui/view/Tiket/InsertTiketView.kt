package com.example.finalproject.ui.view.Tiket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.model.Event
import com.example.finalproject.model.Peserta
import com.example.finalproject.ui.customwidget.CostumeTopAppBar
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import com.example.finalproject.ui.viewmodel.PenyediaViewModel
import com.example.finalproject.ui.viewmodel.Tiket.InsertTiketUiEvent
import com.example.finalproject.ui.viewmodel.Tiket.InsertTiketUiState
import com.example.finalproject.ui.viewmodel.Tiket.InsertTiketViewModel
import kotlinx.coroutines.launch

object DestinasiEntryTiket: DestinasiNavigasi {
    override val route = "entry tiket"
    override val titleRes = "Insert Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTiketScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTiketViewModel = viewModel(factory = PenyediaViewModel.Factory),
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                judul = "Insert Data Tiket"
            )
        }
    ) { innerPadding ->
        EntryBodyTiket(
            insertTiketUiState = viewModel.uiTiketState,
            onTiketValueChange = viewModel::updateInsertTiketState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTiket()
                    navigateBack()
                }
            },
            eventList = viewModel.eventList,
            pesertaList = viewModel.pesertaList,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyTiket(
    insertTiketUiState: InsertTiketUiState,
    onTiketValueChange: (InsertTiketUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    eventList : List<Event>,
    pesertaList : List<Peserta>,
    modifier: Modifier = Modifier
){

    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertTiketUiEvent = insertTiketUiState.insertTiketUiEvent,
            onValueChange = onTiketValueChange,
            eventList = eventList,
            pesertaList = pesertaList,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertTiketUiEvent: InsertTiketUiEvent,
    eventList: List<Event>,
    pesertaList: List<Peserta>,
    modifier: Modifier = Modifier,
    onValueChange: (InsertTiketUiEvent) -> Unit = {},

    ) {
    var expandedEvent by remember { mutableStateOf(false) }
    var expandedPeserta by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertTiketUiEvent.id_tiket,
            onValueChange = { onValueChange(insertTiketUiEvent.copy(id_tiket = it)) },
            label = { Text("Id Tiket") },
            placeholder = { Text("Masukkan ID Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            singleLine = true
        )

        ExposedDropdownMenuBox(
            expanded = expandedPeserta,
            onExpandedChange = { expandedPeserta = !expandedPeserta }
        ) {
            OutlinedTextField(
                value = "${insertTiketUiEvent.id_peserta} - ${insertTiketUiEvent.nama_peserta}",
                onValueChange = { },
                label = { Text("Pilih Peserta") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPeserta)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true
            )

            ExposedDropdownMenu(
                expanded = expandedPeserta,
                onDismissRequest = { expandedPeserta = false }
            ) {
                pesertaList.forEach { peserta ->
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(
                                insertTiketUiEvent.copy(
                                    id_peserta = peserta.id_peserta,
                                    nama_peserta = peserta.nama_peserta
                                )
                            )
                            expandedPeserta = false
                        },
                        text = { Text(text = "${peserta.id_peserta} - ${peserta.nama_peserta}") }
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = expandedEvent,
            onExpandedChange = { expandedEvent = !expandedEvent }
        ) {
            OutlinedTextField(
                value = "${insertTiketUiEvent.id_event} - ${insertTiketUiEvent.nama_event}",
                onValueChange = { },
                label = { Text("Pilih Event") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEvent)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true
            )

            ExposedDropdownMenu(
                expanded = expandedEvent,
                onDismissRequest = { expandedEvent = false }
            ) {
                eventList.forEach { event ->
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(
                                insertTiketUiEvent.copy(
                                    id_event = event.id_event,
                                    nama_event = event.nama_event,
                                    tanggal_event = event.tanggal_event,
                                    lokasi_event = event.lokasi_event
                                )
                            )
                            expandedEvent = false
                        },
                        text = { Text(text = "${event.id_event} - ${event.nama_event}") }
                    )
                }
            }
        }

        // Menampilkan Tanggal Event yang diambil dari pilihan Event
        OutlinedTextField(
            value = insertTiketUiEvent.tanggal_event,
            onValueChange = { },
            label = { Text("Tanggal Event") },
            placeholder = { Text("Tanggal Event akan muncul setelah memilih Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false, // Tanggal ini hanya bisa dibaca, tidak bisa diubah
            singleLine = true
        )

        OutlinedTextField(
            value = insertTiketUiEvent.lokasi_event,
            onValueChange = { },
            label = { Text("Lokasi Event") },
            placeholder = { Text("Lokasi Event akan muncul setelah memilih Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false, // Lokasi ini hanya bisa dibaca, tidak bisa diubah
            singleLine = true
        )

        // Kapasitas Tiket
        OutlinedTextField(
            value = insertTiketUiEvent.kapasitas_tiket,
            onValueChange = { onValueChange(insertTiketUiEvent.copy(kapasitas_tiket = it)) },
            label = { Text(text = "Kapasitas Tiket") },
            placeholder = { Text("Masukkan Jumlah Tiket yang tersedia") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            singleLine = true
        )

        // Harga Tiket
        OutlinedTextField(
            value = insertTiketUiEvent.harga_tiket,
            onValueChange = { onValueChange(insertTiketUiEvent.copy(harga_tiket = it)) },
            label = { Text(text = "Harga Tiket") },
            placeholder = { Text("Masukkan Harga Tiket") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = true,
            singleLine = true
        )

    }
}