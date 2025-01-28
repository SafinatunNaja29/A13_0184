package com.example.finalproject.ui.view.Tiket

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
import com.example.finalproject.model.Tiket
import com.example.finalproject.ui.customwidget.CostumeTopAppBar
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import com.example.finalproject.ui.view.Peserta.OnError
import com.example.finalproject.ui.view.Peserta.OnLoading
import com.example.finalproject.ui.viewmodel.PenyediaViewModel
import com.example.finalproject.ui.viewmodel.Tiket.HomeTiketUiState
import com.example.finalproject.ui.viewmodel.Tiket.HomeTiketViewModel

object DestinasiHomeTiket: DestinasiNavigasi {
    override val route = "home tiket"
    override val titleRes = "Home Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTiketScreen(
    navigateBack: () -> Unit,
    navigateToItemEntry:() -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = viewModel::getTiket,
                navigateUp = navigateBack,
                judul = "Daftar Tiket"
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Tiket")
            }
        },
    ) { innerPadding ->
        HomeTiketStatus(
            homeTiketUiState = viewModel.tiketUiState,
            retryAction = {viewModel.getTiket()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteTiket(it.id_tiket)
                viewModel.getTiket()
            }
        )
    }
}

@Composable
fun HomeTiketStatus(
    homeTiketUiState: HomeTiketUiState,
    retryAction: () -> Unit,
    modifier: Modifier,
    onDeleteClick: (Tiket) -> Unit,
    onDetailClick: (String) -> Unit
){
    when {
        homeTiketUiState is HomeTiketUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        homeTiketUiState is HomeTiketUiState.Success ->
            if(homeTiketUiState.tiket.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Data Tiket belum ditambahkan.",
                        fontWeight = FontWeight.Bold)
                }
            }else{
                TiketLayout(
                    tiket = homeTiketUiState.tiket,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_tiket) },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        homeTiketUiState is HomeTiketUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun TiketLayout(
    tiket: List<Tiket>,
    modifier: Modifier = Modifier,
    onDetailClick: (Tiket) -> Unit,
    onDeleteClick: (Tiket) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tiket) { tiket ->
            TiketCard(
                tiket = tiket,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(tiket) },
                onDeleteClick = { onDeleteClick(tiket) }
            )
        }
    }
}

@Composable
fun TiketCard(
    tiket: Tiket,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tiket) -> Unit = {}
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    // Menghitung persentase kapasitas yang tersedia
    val kapasitasTersedia = tiket.kapasitas_tiket.toIntOrNull() ?: 0
    val kapasitasTotal = 100 // Asumsi kapasitas total
    val persentaseTersedia = (kapasitasTersedia.toFloat() / kapasitasTotal) * 100

    // Menentukan warna berdasarkan persentase kapasitas
    val warnaTiket = when {
        kapasitasTersedia == 0 -> Color.Red
        persentaseTersedia > 50 -> Color.Green
        else -> Color.Yellow
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = warnaTiket),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Id Tiket: ${tiket.id_tiket}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { deleteConfirmationRequired = true }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            onDeleteClick(tiket)
                            deleteConfirmationRequired = false
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false }
                    )
                }
            }
            Text(
                text = "Event: ${tiket.nama_event}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Peserta: ${tiket.nama_peserta}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Tanggal Event: ${tiket.tanggal_event}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Lokasi Event: ${tiket.lokasi_event}",
                style = MaterialTheme.typography.titleMedium
            )

            if (kapasitasTersedia == 0) {
                Text(
                    text = "Sold Out",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
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