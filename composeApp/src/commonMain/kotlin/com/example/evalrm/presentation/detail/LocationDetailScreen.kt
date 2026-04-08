package com.example.evalrm.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LocationDetailScreen(
    modifier: Modifier = Modifier,
    state: LocationDetailUiState,
    onBack: (() -> Unit)? = null,
    onRetry: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (onBack != null) {
            TextButton(onClick = onBack) {
                Text("Retour")
            }
        }

        when {
            state.isLoading -> CircularProgressIndicator()
            state.errorMessage != null -> {
                Text(text = state.errorMessage, color = MaterialTheme.colorScheme.error)
                if (onRetry != null) {
                    TextButton(onClick = onRetry) { Text("Reessayer") }
                }
            }

            state.location == null -> {
                Text("Selectionne une location pour afficher son detail.")
            }

            else -> {
                Text(text = state.location.name, style = MaterialTheme.typography.headlineSmall)
                Text(text = "Type: ${state.location.type}")
                Text(text = "Dimension: ${state.location.dimension}")
                Text(text = "Residents: ${state.location.residentsCount}")
                Text(
                    text = if (state.location.residentsCount == 0) {
                        "Aucun resident connu"
                    } else {
                        "Cette location contient des residents connus."
                    },
                )
            }
        }
    }
}
