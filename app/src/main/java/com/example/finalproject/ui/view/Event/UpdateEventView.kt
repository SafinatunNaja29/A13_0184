package com.example.finalproject.ui.view.Event

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.ui.customwidget.CostumeTopAppBar
import com.example.finalproject.ui.navigation.DestinasiNavigasi
import com.example.finalproject.ui.viewmodel.Event.UpdateEventViewModel
import com.example.finalproject.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateEvent : DestinasiNavigasi {
    override val route = "update event"
    const val ID_EVENT = "id_event"
    override val titleRes = "Edit Event"
    val routeWithArg = "$route/{$ID_EVENT}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateEventView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateEventViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val insertEventUiState = viewModel.updateEventUIState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                judul = "Update Data Event"
            )
        }
    ) { innerPadding ->
        EntryBodyEvent(
            insertEventUiState = insertEventUiState,
            onEventValueChange = { viewModel.updateEventState(it) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateEvent()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}