package com.example.finalproject.ui.view.Peserta

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject.model.Peserta
import com.example.finalproject.ui.viewmodel.Peserta.DetailPesertaUiState

@Composable
fun BodyDetailPeserta(
    detailPesertaUiState: DetailPesertaUiState,
    modifier: Modifier = Modifier
) {
    when {
        detailPesertaUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailPesertaUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailPesertaUiState.errorMessage,
                    color = Color.Red
                )
            }
        }
        detailPesertaUiState.isUiPesertaNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailPeserta(
                    peserta = detailPesertaUiState.detailPesertaUiEvent.toPeserta(),
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ItemDetailPeserta(
    modifier: Modifier = Modifier,
    peserta: Peserta
){
    Card(
        modifier = modifier.fillMaxWidth().padding(top = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            ComponentDetailPeserta(judul = "Id Peserta", isinya = peserta.id_peserta)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPeserta(judul = "Nama Peserta", isinya = peserta.nama_peserta)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPeserta(judul = "Email", isinya = peserta.email)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPeserta(judul = "Nomor Telepon", isinya = peserta.nomor_telepon)
        }
    }
}

@Composable
fun ComponentDetailPeserta(
    modifier: Modifier = Modifier,
    judul:String,
    isinya:String
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}