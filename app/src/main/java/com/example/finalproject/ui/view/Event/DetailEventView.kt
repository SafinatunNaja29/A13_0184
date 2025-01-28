package com.example.finalproject.ui.view.Event

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
import com.example.finalproject.model.Event
import com.example.finalproject.ui.customwidget.CostumeTopAppBar
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import com.example.finalproject.ui.viewmodel.Event.DetailEventUiState
import com.example.finalproject.ui.viewmodel.Event.DetailEventViewModel
import com.example.finalproject.ui.viewmodel.Event.toEvent
import com.example.finalproject.ui.viewmodel.PenyediaViewModel

object DestinasiDetailEvent: DestinasiNavigasi {
    override val route = "detail event"
    const val ID_EVENT = "id_event"
    override val titleRes = "Detail Event"
    val routeWithArg = "$route/{$ID_EVENT}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailEventView(
    navigateBack: () -> Unit,
    navigateToEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailEventViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = viewModel::getEventById,
                navigateUp = navigateBack,
                judul = "Detail data Event"
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
                    contentDescription = "Edit Event"
                )
            }
        }, containerColor = Color(0xFFececec)
    ) { innerPadding ->
        BodyDetailEvent(
            detailEventUiState = viewModel.detailEventUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyDetailEvent(
    detailEventUiState: DetailEventUiState,
    modifier: Modifier = Modifier
) {
    when {
        detailEventUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailEventUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailEventUiState.errorMessage,
                    color = Color.Red
                )
            }
        }
        detailEventUiState.isUiEventNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailEvent(
                    event = detailEventUiState.detailEventUiEvent.toEvent(),
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ItemDetailEvent(
    modifier: Modifier = Modifier,
    event: Event
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
            ComponentDetailEvent(judul = "Id Event", isinya = event.id_event)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailEvent(judul = "Nama Event", isinya = event.nama_event)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailEvent(judul = "Deskripsi Event", isinya = event.deskripsi_event)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailEvent(judul = "Lokasi Event", isinya = event.lokasi_event)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailEvent(judul = "Tanggal Event", isinya = event.tanggal_event)
        }
    }
}

@Composable
fun ComponentDetailEvent(
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