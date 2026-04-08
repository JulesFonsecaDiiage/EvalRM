package com.example.evalrm.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evalrm.domain.model.Location

@Composable
fun LocationListScreen(
    modifier: Modifier = Modifier,
    state: LocationListUiState,
    onRetry: () -> Unit,
    onLocationClick: (Location) -> Unit,
    selectedLocationId: Int? = null,
) {
    when {
        state.isLoading -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }

        state.errorMessage != null -> {
            Column(modifier = modifier.padding(16.dp)) {
                Text(text = state.errorMessage, color = MaterialTheme.colorScheme.error)
                TextButton(onClick = onRetry) { Text("Reessayer") }
            }
        }

        else -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(state.locations, key = { it.id }) { location ->
                    LocationListItem(
                        location = location,
                        isSelected = selectedLocationId == location.id,
                        onClick = { onLocationClick(location) },
                    )
                }
            }
        }
    }
}

@Composable
private fun LocationListItem(
    location: Location,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = if (isSelected) {
            CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        } else {
            CardDefaults.cardColors()
        },
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = location.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Type: ${location.type}")
            Text(text = "Dimension: ${location.dimension}")
            Text(text = "Residents: ${location.residentsCount}")
        }
    }
}
