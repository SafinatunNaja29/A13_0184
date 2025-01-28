package com.example.finalproject.ui.view.Transaksi

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
import androidx.compose.runtime.collectAsState
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
import com.example.finalproject.model.Tiket
import com.example.finalproject.ui.customwidget.CostumeTopAppBar
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import com.example.finalproject.ui.viewmodel.PenyediaViewModel
import com.example.finalproject.ui.viewmodel.Transaksi.InsertTransaksiUiEvent
import com.example.finalproject.ui.viewmodel.Transaksi.InsertTransaksiUiState
import com.example.finalproject.ui.viewmodel.Transaksi.InsertTransaksiViewModel
import kotlinx.coroutines.launch

object DestinasiEntryTransaksi: DestinasiNavigasi {
    override val route = "entry transaksi"
    override val titleRes = "Insert Transaksi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTransaksiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val tiketList by viewModel.tiketList.collectAsState(emptyList()) // Ambil data tiket

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                canNavigateBack = true,
                scrollBehavior = scrollBehaviour,
                navigateUp = navigateBack,
                judul = "Insert Data Transaksi"
            )
        }
    ) { innerPadding ->
        EntryBodyTransaksi(
            insertTransaksiUiState = viewModel.uiTransaksiState,
            onTransaksiValueChange = viewModel::updateInsertTransaksiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTransaksi()
                    navigateBack()
                }},
            tiketList = tiketList,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyTransaksi(
    insertTransaksiUiState: InsertTransaksiUiState,
    onTransaksiValueChange: (InsertTransaksiUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    tiketList: List<Tiket>,
    modifier: Modifier = Modifier
){

    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertTransaksiUiEvent = insertTransaksiUiState.insertTransaksiUiEvent,
            onValueChange = onTransaksiValueChange,
            tiketList = tiketList,
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
    insertTransaksiUiEvent: InsertTransaksiUiEvent,
    tiketList: List<Tiket>,
    modifier: Modifier = Modifier,
    onValueChange: (InsertTransaksiUiEvent) -> Unit = {},

    ){
    var expanded by remember { mutableStateOf(false) } // State untuk dropdown
    var selectedTiket by remember { mutableStateOf<Tiket?>(null) } // Tiket yang dipilih

    if (insertTransaksiUiEvent.id_tiket.isNotEmpty() && selectedTiket == null) {
        selectedTiket = tiketList.find { it.id_tiket == insertTransaksiUiEvent.id_tiket }
    }

    // State untuk menyimpan status error
    var isError by remember { mutableStateOf(false) }

    var isJumlahTiketExceeded by remember { mutableStateOf(false) } // State untuk validasi kapasitas tiket


    // Hitung jumlah pembayaran berdasarkan jumlah tiket dan harga tiket
    val jumlahPembayaran = remember(selectedTiket, insertTransaksiUiEvent.jumlah_tiket) {
        val jumlahTiket = insertTransaksiUiEvent.jumlah_tiket.toIntOrNull() ?: 0
        val hargaTiket = selectedTiket?.harga_tiket?.toIntOrNull() ?: 0 // Pastikan hargaTiket di-parsing ke Int
        (jumlahTiket * hargaTiket).toString()
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertTransaksiUiEvent.id_transaksi,
            onValueChange = {
                onValueChange(insertTransaksiUiEvent.copy(id_transaksi = it))
                isError = it.isEmpty() // Set error jika kosong
            },
            label = { Text("Id Transaksi") },
            placeholder = { Text("Masukkan ID Transaksi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            singleLine = true,
            isError = isError && insertTransaksiUiEvent.id_transaksi.isEmpty(),
            supportingText = {
                if (isError && insertTransaksiUiEvent.id_transaksi.isEmpty()) {
                    Text("ID Transaksi tidak boleh kosong", color = Color.Red)
                }
            }
        )

        // Dropdown untuk memilih Tiket
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedTiket?.let { "${it.id_tiket} - ${it.nama_event} - ${it.nama_peserta}" } ?: "",
                onValueChange = { }, // Tidak perlu diubah manual
                label = { Text("Tiket") },
                placeholder = { Text("Pilih Tiket") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true, // Hanya bisa dipilih dari dropdown
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                tiketList.forEach { tiket ->
                    DropdownMenuItem(
                        text = { Text("${tiket.id_tiket} - ${tiket.nama_event} - ${tiket.harga_tiket} - ${tiket.nama_peserta}") },
                        onClick = {
                            selectedTiket = tiket
                            val jumlahTiket = insertTransaksiUiEvent.jumlah_tiket.toIntOrNull() ?: 0
                            val hargaTiket = tiket.harga_tiket.toIntOrNull() ?: 0
                            val jumlahPembayaran = (jumlahTiket * hargaTiket).toString()

                            onValueChange(
                                insertTransaksiUiEvent.copy(
                                    id_tiket = tiket.id_tiket,
                                    nama_event = tiket.nama_event,
                                    nama_peserta = tiket.nama_peserta,
                                    jumlah_pembayaran = jumlahPembayaran // Update jumlah pembayaran ke state
                                )
                            )
                            expanded = false
                        }

                    )
                }
            }
        }

        OutlinedTextField(
            value = insertTransaksiUiEvent.nama_event,
            onValueChange = { },
            label = { Text("Nama Event") },
            placeholder = { Text("Nama Event akan muncul setelah memilih Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            singleLine = true
        )

        OutlinedTextField(
            value = insertTransaksiUiEvent.nama_peserta,
            onValueChange = { },
            label = { Text("Nama Peserta") },
            placeholder = { Text("Nama Peserta akan muncul setelah memilih Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            singleLine = true
        )

        // Field Jumlah Tiket
        OutlinedTextField(
            value = insertTransaksiUiEvent.jumlah_tiket,
            onValueChange = { newJumlahTiket ->
                val jumlahTiket = newJumlahTiket.toIntOrNull() ?: 0
                val hargaTiket = selectedTiket?.harga_tiket?.toIntOrNull() ?: 0
                val kapasitasTiket = selectedTiket?.kapasitas_tiket?.toIntOrNull() ?: 0

                // Validasi jumlah tiket
                isJumlahTiketExceeded = jumlahTiket > kapasitasTiket
                isError = newJumlahTiket.isEmpty() || isJumlahTiketExceeded

                // Hitung jumlah pembayaran
                val jumlahPembayaran = if (isJumlahTiketExceeded) {
                    "" // Set kosong jika melebihi kapasitas
                } else {
                    (jumlahTiket * hargaTiket).toString()
                }

                onValueChange(
                    insertTransaksiUiEvent.copy(
                        jumlah_tiket = newJumlahTiket,
                        jumlah_pembayaran = jumlahPembayaran // Update jumlah pembayaran ke state
                    )
                )
            },
            label = { Text("Jumlah Tiket") },
            placeholder = { Text("Masukkan Jumlah Tiket") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = true,
            singleLine = true,
            isError = isError && (insertTransaksiUiEvent.jumlah_tiket.isEmpty() || isJumlahTiketExceeded),
            supportingText = {
                if (isError) {
                    if (insertTransaksiUiEvent.jumlah_tiket.isEmpty()) {
                        Text("Jumlah Tiket tidak boleh kosong", color = Color.Red)
                    } else if (isJumlahTiketExceeded) {
                        Text("Jumlah tiket melebihi batas", color = Color.Red)
                    }
                }
            }
        )


        // Field Jumlah Pembayaran (non-editable)
        OutlinedTextField(
            value = jumlahPembayaran,
            onValueChange = { }, // Tidak bisa diubah manual
            label = { Text("Jumlah Pembayaran") },
            placeholder = { Text("Jumlah Pembayaran") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = false, // Non-editable
            singleLine = true
        )

        // Field Tanggal Transaksi
        OutlinedTextField(
            value = insertTransaksiUiEvent.tanggal_transaksi,
            onValueChange = {
                onValueChange(insertTransaksiUiEvent.copy(tanggal_transaksi = it))
                isError = it.isEmpty() // Set error jika kosong
            },
            label = { Text(text = "Tanggal Transaksi") },
            placeholder = { Text("Masukkan Tanggal Transaksi") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = true,
            singleLine = true,
            isError = isError && insertTransaksiUiEvent.tanggal_transaksi.isEmpty(),
            supportingText = {
                if (isError && insertTransaksiUiEvent.tanggal_transaksi.isEmpty()) {
                    Text("Tanggal Transaksi tidak boleh kosong", color = Color.Red)
                }
            }
        )
    }
}