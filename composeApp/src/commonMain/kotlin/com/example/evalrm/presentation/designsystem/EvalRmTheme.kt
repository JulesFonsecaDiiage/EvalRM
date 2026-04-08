package com.example.evalrm.presentation.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun EvalRmTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EvalRmLightColorScheme,
        typography = EvalRmTypography,
        shapes = EvalRmShapes,
        content = content,
    )
}

