package com.example.evalrm

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.evalrm.core.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "EvalRM",
        ) {
            App(isDesktop = true)
        }
    }
}