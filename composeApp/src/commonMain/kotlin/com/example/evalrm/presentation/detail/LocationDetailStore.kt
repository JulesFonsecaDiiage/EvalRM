package com.example.evalrm.presentation.detail

import com.example.evalrm.domain.usecase.GetLocationDetailUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationDetailStore(
    private val getLocationDetailUseCase: GetLocationDetailUseCase,
    private val scope: CoroutineScope,
) {
    private val _state = MutableStateFlow(LocationDetailUiState())
    val state: StateFlow<LocationDetailUiState> = _state.asStateFlow()
    private var loadJob: Job? = null

    fun onIntent(intent: LocationDetailIntent) {
        when (intent) {
            is LocationDetailIntent.Load -> load(intent.locationId, forceRefresh = false)
            is LocationDetailIntent.Retry -> load(intent.locationId, forceRefresh = true)
            LocationDetailIntent.Clear -> {
                loadJob?.cancel()
                _state.value = LocationDetailUiState()
            }
        }
    }

    private fun load(locationId: Int, forceRefresh: Boolean) {
        loadJob?.cancel()
        loadJob = scope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            runCatching { getLocationDetailUseCase(locationId, forceRefresh) }
                .onSuccess { location ->
                    _state.value = LocationDetailUiState(location = location)
                }
                .onFailure { throwable ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = throwable.message ?: "Erreur inconnue pendant le chargement du detail.",
                    )
                }
        }
    }
}
