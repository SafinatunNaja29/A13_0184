package com.example.finalproject.ui.view.Peserta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.finalproject.ui.viewmodel.Peserta.InsertPesertaUiEvent

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