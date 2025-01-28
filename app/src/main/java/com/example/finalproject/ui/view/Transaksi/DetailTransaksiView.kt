package com.example.finalproject.ui.view.Transaksi

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
import com.example.finalproject.model.Transaksi
import com.example.finalproject.ui.customwidget.CostumeTopAppBar
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import com.example.finalproject.ui.viewmodel.PenyediaViewModel
import com.example.finalproject.ui.viewmodel.Transaksi.DetailTransaksiUiState
import com.example.finalproject.ui.viewmodel.Transaksi.DetailTransaksiViewModel
import com.example.finalproject.ui.viewmodel.Transaksi.toTransaksi

object DestinasiDetailTransaksi: DestinasiNavigasi {
    override val route = "detail Transaksi"
    const val ID_TRANSAKSI = "id_transaksi"
    override val titleRes = "Detail Transaksi"
    val routeWithArg = "$route/{$ID_TRANSAKSI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTransaksiView(
    navigateBack: () -> Unit,
    navigateToEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailTransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = viewModel::getTransaksiById,
                navigateUp = navigateBack,
                judul = "Detail Data Transaksi"
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
                    contentDescription = "Edit Transaksi"
                )
            }
        }, containerColor = Color(0xFFececec)
    ) { innerPadding ->
        BodyDetailTransaksi(
            detailTransaksiUiState = viewModel.detailTransaksiUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyDetailTransaksi(
    detailTransaksiUiState: DetailTransaksiUiState,
    modifier: Modifier = Modifier
) {
    when {
        detailTransaksiUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailTransaksiUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailTransaksiUiState.errorMessage,
                    color = Color.Red
                )
            }
        }
        detailTransaksiUiState.isUiTransaksiNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailTransaksi(
                    transaksi = detailTransaksiUiState.detailTransaksiUiEvent.toTransaksi(),
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ItemDetailTransaksi(
    modifier: Modifier = Modifier,
    transaksi: Transaksi
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
            ComponentDetailTransaksi(judul = "Id Transaksi", isinya = transaksi.id_transaksi)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTransaksi(judul = "Nama Event", isinya = transaksi.nama_event)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTransaksi(judul = "Nama Peserta", isinya = transaksi.nama_peserta)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTransaksi(judul = "Jumlah Tiket", isinya = transaksi.jumlah_tiket)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTransaksi(judul = "Jumlah Pembayaran", isinya = transaksi.jumlah_pembayaran)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTransaksi(judul = "Tanggal Transaksi", isinya = transaksi.tanggal_transaksi)
        }
    }
}

@Composable
fun ComponentDetailTransaksi(
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