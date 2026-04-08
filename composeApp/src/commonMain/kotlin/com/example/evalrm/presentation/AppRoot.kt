package com.example.evalrm.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evalrm.cross.audio.rememberAudioManager
import com.example.evalrm.domain.usecase.GetLocationDetailUseCase
import com.example.evalrm.domain.usecase.GetLocationsUseCase
import com.example.evalrm.presentation.detail.LocationDetailIntent
import com.example.evalrm.presentation.detail.LocationDetailScreen
import com.example.evalrm.presentation.detail.LocationDetailStore
import com.example.evalrm.presentation.list.LocationListIntent
import com.example.evalrm.presentation.list.LocationListScreen
import com.example.evalrm.presentation.list.LocationListStore
import com.example.evalrm.presentation.navigation.AppNavigator
import com.example.evalrm.presentation.navigation.AppRoute
import org.koin.core.context.GlobalContext

@Composable
fun AppRoot(isDesktop: Boolean) {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val koin = remember { GlobalContext.get() }
            val getLocationsUseCase = remember(koin) { koin.get<GetLocationsUseCase>() }
            val getLocationDetailUseCase = remember(koin) { koin.get<GetLocationDetailUseCase>() }

            val audioManager = rememberAudioManager()
            val scope = rememberCoroutineScope()

            val listStore = remember(scope, getLocationsUseCase) { LocationListStore(getLocationsUseCase, scope) }
            val detailStore = remember(scope, getLocationDetailUseCase) {
                LocationDetailStore(getLocationDetailUseCase, scope)
            }
            val listState by listStore.state.collectAsState()
            val detailState by detailStore.state.collectAsState()

            LaunchedEffect(Unit) {
                listStore.onIntent(LocationListIntent.Load)
            }

            if (isDesktop) {
                var selectedId by remember { mutableStateOf<Int?>(null) }
                Row(modifier = Modifier.fillMaxSize()) {
                    LocationListScreen(
                        state = listState,
                        onRetry = { listStore.onIntent(LocationListIntent.Retry) },
                        onLocationClick = { location ->
                            selectedId = location.id
                            audioManager.playLocationOpened()
                            detailStore.onIntent(LocationDetailIntent.Load(location.id))
                        },
                        selectedLocationId = selectedId,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                    )
                    LocationDetailScreen(
                        state = detailState,
                        onRetry = {
                            val id = selectedId
                            if (id != null) {
                                detailStore.onIntent(LocationDetailIntent.Retry(id))
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    )
                }
            } else {
                val navigator = remember { AppNavigator() }
                when (val route = navigator.route) {
                    AppRoute.List -> {
                        LocationListScreen(
                            state = listState,
                            onRetry = { listStore.onIntent(LocationListIntent.Retry) },
                            onLocationClick = { location ->
                                navigator.openDetail(location.id)
                                audioManager.playLocationOpened()
                                detailStore.onIntent(LocationDetailIntent.Load(location.id))
                            },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }

                    is AppRoute.Detail -> {
                        LocationDetailScreen(
                            state = detailState,
                            onBack = { navigator.backToList() },
                            onRetry = { detailStore.onIntent(LocationDetailIntent.Retry(route.locationId)) },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }
}
