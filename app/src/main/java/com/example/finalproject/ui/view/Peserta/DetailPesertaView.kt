package com.example.finalproject.ui.view.Peserta

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.model.Peserta
import com.example.finalproject.ui.viewmodel.Peserta.DetailPesertaUiState
import com.example.finalproject.ui.viewmodel.Peserta.DetailPesertaViewModel
import com.example.finalproject.ui.viewmodel.Peserta.toPeserta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPesertaView(
    navigateBack: () -> Unit,
    navigateToEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPesertaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = viewModel::getPesertaById,
                navigateUp = navigateBack,
                judul = "Detail Data Peserta"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEdit,
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFF8B0000),
                contentColor = Color.White,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Peserta"
                )
            }
        }, containerColor = Color(0xFFececec)
    ) { innerPadding ->
        BodyDetailPeserta(
            detailPesertaUiState = viewModel.detailPesertaUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

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