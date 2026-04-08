package com.example.evalrm.presentation.detail

import com.example.evalrm.domain.model.Location

data class LocationDetailUiState(
    val isLoading: Boolean = false,
    val location: Location? = null,
    val errorMessage: String? = null,
)

sealed interface LocationDetailIntent {
    data class Load(val locationId: Int) : LocationDetailIntent
    data class Retry(val locationId: Int) : LocationDetailIntent
    data object Clear : LocationDetailIntent
}

