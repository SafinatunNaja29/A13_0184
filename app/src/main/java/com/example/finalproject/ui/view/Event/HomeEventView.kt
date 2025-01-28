package com.example.finalproject.ui.view.Event

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
import com.example.finalproject.model.Event
import com.example.finalproject.ui.customwidget.CostumeTopAppBar
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import com.example.finalproject.ui.view.Peserta.OnError
import com.example.finalproject.ui.view.Peserta.OnLoading
import com.example.finalproject.ui.viewmodel.Event.HomeEventUiState
import com.example.finalproject.ui.viewmodel.Event.HomeEventViewModel
import com.example.finalproject.ui.viewmodel.PenyediaViewModel

object DestinasiHomeEvent: DestinasiNavigasi {
    override val route = "home event"
    override val titleRes = "Home Event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeEventScreen(
    navigateBack: () -> Unit,
    navigateToItemEntry:() -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeEventViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = viewModel::getEvent,
                navigateUp = navigateBack,
                judul = "Daftar Event"
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Event")
            }
        },
    ) { innerPadding ->
        HomeEventStatus(
            homeEventUiState = viewModel.eventUiState,
            retryAction = {viewModel.getEvent()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteEvent(it.id_event)
                viewModel.getEvent()
            }
        )
    }
}

@Composable
fun HomeEventStatus(
    homeEventUiState: HomeEventUiState,
    retryAction: () -> Unit,
    modifier: Modifier,
    onDeleteClick: (Event) -> Unit,
    onDetailClick: (String) -> Unit
){
    when {
        homeEventUiState is HomeEventUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        homeEventUiState is HomeEventUiState.Success ->
            if(homeEventUiState.event.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Data Event belum ditambahkan.",
                        fontWeight = FontWeight.Bold)
                }
            }else{
                EventLayout(
                    event = homeEventUiState.event,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_event) },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        homeEventUiState is HomeEventUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun EventLayout(
    event: List<Event>,
    modifier: Modifier = Modifier,
    onDetailClick: (Event) -> Unit,
    onDeleteClick: (Event) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(event){event->
            EventCard(
                event = event,
                modifier = Modifier.fillMaxWidth()
                    .clickable{ onDetailClick(event) },
                onDeleteClick ={
                    onDeleteClick(event)
                }
            )
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    modifier: Modifier = Modifier,
    onDeleteClick: (Event) -> Unit = {}
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
                    text = "Id Event: ${event.id_event}",
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
                            onDeleteClick(event)
                            deleteConfirmationRequared = false
                        },
                        onDeleteCancel = { deleteConfirmationRequared = false }
                    )
                }
            }
            Text(
                text = "Nama Event: ${event.nama_event}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Tanggal Event: ${event.tanggal_event}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Lokasi Event: ${event.lokasi_event}",
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