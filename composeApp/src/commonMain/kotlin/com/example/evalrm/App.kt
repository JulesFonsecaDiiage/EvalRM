package com.example.evalrm

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.evalrm.core.AppDependencies
import com.example.evalrm.presentation.AppRoot

@Composable
@Preview
fun App(isDesktop: Boolean = false) {
    AppDependencies.init()
    AppRoot(isDesktop = isDesktop)
}