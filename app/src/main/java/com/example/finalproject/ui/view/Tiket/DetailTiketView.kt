package com.example.finalproject.ui.view.Tiket

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
import com.example.finalproject.model.Tiket
import com.example.finalproject.ui.customwidget.CostumeTopAppBar
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import com.example.finalproject.ui.viewmodel.PenyediaViewModel
import com.example.finalproject.ui.viewmodel.Tiket.DetailTiketUiState
import com.example.finalproject.ui.viewmodel.Tiket.DetailTiketViewModel
import com.example.finalproject.ui.viewmodel.Tiket.toTiket

object DestinasiDetailTiket: DestinasiNavigasi {
    override val route = "detail Tiket"
    const val ID_TIKET = "id_tiket"
    override val titleRes = "Detail Tiket"
    val routeWithArg = "$route/{$ID_TIKET}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTiketView(
    navigateBack: () -> Unit,
    navigateToEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = viewModel::getTiketById,
                navigateUp = navigateBack,
                judul = "Detail Data Tiket"
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
                    contentDescription = "Edit Tiket"
                )
            }
        }, containerColor = Color(0xFFececec)
    ) { innerPadding ->
        BodyDetailTiket(
            detailTiketUiState = viewModel.detailTiketUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyDetailTiket(
    detailTiketUiState: DetailTiketUiState,
    modifier: Modifier = Modifier
) {
    when {
        detailTiketUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailTiketUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailTiketUiState.errorMessage,
                    color = Color.Red
                )
            }
        }
        detailTiketUiState.isUiTiketNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailTiket(
                    tiket = detailTiketUiState.detailTiketUiEvent.toTiket(),
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ItemDetailTiket(
    modifier: Modifier = Modifier,
    tiket: Tiket
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
            ComponentDetailTiket(judul = "Id Tiket", isinya = tiket.id_tiket)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTiket(judul = "Nama Event", isinya = tiket.nama_event)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTiket(judul = "Nama Peserta", isinya = tiket.nama_peserta)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTiket(judul = "Tanggal Event", isinya = tiket.tanggal_event)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTiket(judul = "Lokasi Event", isinya = tiket.lokasi_event)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTiket(judul = "Harga Tiket", isinya = tiket.harga_tiket)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTiket(judul = "kapasitas Tiket", isinya = tiket.kapasitas_tiket)
        }
    }
}

@Composable
fun ComponentDetailTiket(
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