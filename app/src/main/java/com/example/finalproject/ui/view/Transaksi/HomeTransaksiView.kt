package com.example.finalproject.ui.view.Transaksi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.model.Transaksi
import com.example.finalproject.ui.customwidget.CostumeTopAppBar
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import com.example.finalproject.ui.view.Peserta.OnError
import com.example.finalproject.ui.view.Peserta.OnLoading
import com.example.finalproject.ui.viewmodel.PenyediaViewModel
import com.example.finalproject.ui.viewmodel.Transaksi.HomeTransaksiUiState
import com.example.finalproject.ui.viewmodel.Transaksi.HomeTransaksiViewModel

object DestinasiHomeTransaksi: DestinasiNavigasi {
    override val route = "home transaksi"
    override val titleRes = "Home Transaksi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTransaksiScreen(
    navigateBack: () -> Unit,
    navigateToItemEntry:() -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeTransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = viewModel::getTransaksi,
                navigateUp = navigateBack,
                judul = "Daftar Transaksi"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFF8B0000),
                contentColor = Color.White,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transaksi")
            }
        },
    ) { innerPadding ->
        HomeTransaksiStatus(
            homeTransaksiUiState = viewModel.transaksiUiState,
            retryAction = {viewModel.getTransaksi()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteTransaksi(it.id_transaksi)
                viewModel.getTransaksi()
            }
        )
    }
}

@Composable
fun HomeTransaksiStatus(
    homeTransaksiUiState: HomeTransaksiUiState,
    retryAction: () -> Unit,
    modifier: Modifier,
    onDeleteClick: (Transaksi) -> Unit,
    onDetailClick: (String) -> Unit
){
    when {
        homeTransaksiUiState is HomeTransaksiUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        homeTransaksiUiState is HomeTransaksiUiState.Success ->
            if(homeTransaksiUiState.transaksi.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Data Transaksi belum ditambahkan.",
                        fontWeight = FontWeight.Bold)
                }
            }else{
                TransaksiLayout(
                    transaksi = homeTransaksiUiState.transaksi,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_transaksi) },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        homeTransaksiUiState is HomeTransaksiUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun TransaksiLayout(
    transaksi: List<Transaksi>,
    modifier: Modifier = Modifier,
    onDetailClick: (Transaksi) -> Unit,
    onDeleteClick: (Transaksi) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(transaksi){transaksi->
            TransaksiCard(
                transaksi = transaksi,
                modifier = Modifier.fillMaxWidth()
                    .clickable{ onDetailClick(transaksi) },
                onDeleteClick ={
                    onDeleteClick(transaksi)
                }
            )
        }
    }
}

@Composable
fun TransaksiCard(
    transaksi: Transaksi,
    modifier: Modifier = Modifier,
    onDeleteClick: (Transaksi) -> Unit = {}
){
    var deleteConfirmationRequared by rememberSaveable { mutableStateOf(false) }
    Card (
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Id Transaksi: ${transaksi.id_transaksi}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { deleteConfirmationRequared = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
                if(deleteConfirmationRequared){
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            onDeleteClick(transaksi)
                            deleteConfirmationRequared = false
                        },
                        onDeleteCancel = { deleteConfirmationRequared = false }
                    )
                }
            }
            Text(
                text = "Nama Event: ${transaksi.nama_event}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Nama Peserta: ${transaksi.nama_peserta}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Jumlah Pembayaran: ${transaksi.jumlah_pembayaran}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Tanggal Transaksi: ${transaksi.tanggal_transaksi}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(onDismissRequest = {/*Do Nothing*/},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Yes")
            }
        })
}