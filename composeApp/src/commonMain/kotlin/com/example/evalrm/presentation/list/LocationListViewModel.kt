package com.example.evalrm.presentation.list

import com.example.evalrm.domain.usecase.GetLocationsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationListViewModel(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val scope: CoroutineScope,
) {
    private val _state = MutableStateFlow(LocationListUiState())
    val state: StateFlow<LocationListUiState> = _state.asStateFlow()

    fun onIntent(intent: LocationListIntent) {
        when (intent) {
            LocationListIntent.Load -> load(forceRefresh = false)
            LocationListIntent.Retry -> load(forceRefresh = true)
        }
    }

    private fun load(forceRefresh: Boolean) {
        scope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            runCatching { getLocationsUseCase(forceRefresh) }
                .onSuccess { locations ->
                    _state.value = LocationListUiState(locations = locations)
                }
                .onFailure { throwable ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = throwable.message ?: "Erreur inconnue pendant le chargement de la liste.",
                    )
                }
        }
    }
}
