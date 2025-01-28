package com.example.finalproject.ui.view.Peserta

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
import com.example.finalproject.ui.viewmodel.PenyediaViewModel
import com.example.finalproject.ui.viewmodel.Peserta.InsertPesertaUiEvent
import com.example.finalproject.ui.viewmodel.Peserta.InsertPesertaUiState
import com.example.finalproject.ui.viewmodel.Peserta.InsertPesertaViewModel
import kotlinx.coroutines.launch

object DestinasiEntryPeserta: DestinasiNavigasi {
    override val route = "entry peserta"
    override val titleRes = "Insert Peserta"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPesertaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPesertaViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                judul = "Insert Data Peserta"
            )
        }
    ) { innerPadding ->
        EntryBodyPeserta(
            insertPesertaUiState = viewModel.uiPesertaState,
            onPesertaValueChange = viewModel::updateInsertPesertaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPeserta()
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
fun EntryBodyPeserta(
    insertPesertaUiState: InsertPesertaUiState,
    onPesertaValueChange: (InsertPesertaUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertPesertaUiEvent = insertPesertaUiState.insertPesertaUiEvent,
            onValueChange = onPesertaValueChange,
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
    insertPesertaUiEvent: InsertPesertaUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPesertaUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("ID Peserta :", style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(
            value = insertPesertaUiEvent.id_peserta,
            onValueChange = { onValueChange(insertPesertaUiEvent.copy(id_peserta = it)) },
            label = { Text("Id Peserta") },
            placeholder = { Text("Masukkan ID Peserta") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = enabled,
            singleLine = true
        )

        Text("Nama Peserta :", style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(
            value = insertPesertaUiEvent.nama_peserta,
            onValueChange = { onValueChange(insertPesertaUiEvent.copy(nama_peserta = it)) },
            label = { Text(text = "Nama Event") },
            placeholder = { Text("Masukkan Nama Peserta") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        Text("Email :", style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(
            value = insertPesertaUiEvent.email,
            onValueChange = { onValueChange(insertPesertaUiEvent.copy(email = it)) },
            label = { Text(text = "Email") },
            placeholder = { Text("Masukkan Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            enabled = enabled,
            singleLine = true
        )

        Text("Nomor Telepon :", style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(
            value = insertPesertaUiEvent.nomor_telepon,
            onValueChange = { onValueChange(insertPesertaUiEvent.copy(nomor_telepon = it)) },
            label = { Text(text = "Nomor Telepon") },
            placeholder = { Text("Masukkan Nomor Telepon") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = enabled,
            singleLine = true
        )
    }
}