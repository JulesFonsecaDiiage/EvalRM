package com.example.evalrm.presentation.list

import com.example.evalrm.domain.model.Location

data class LocationListUiState(
    val isLoading: Boolean = false,
    val locations: List<Location> = emptyList(),
    val errorMessage: String? = null,
)

sealed interface LocationListIntent {
    data object Load : LocationListIntent
    data object Retry : LocationListIntent
}
