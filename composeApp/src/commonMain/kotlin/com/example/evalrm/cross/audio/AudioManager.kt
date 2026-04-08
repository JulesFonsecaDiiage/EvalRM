package com.example.evalrm.cross.audio

import androidx.compose.runtime.Composable

interface AudioManager {
    fun playLocationOpened()
}

@Composable
expect fun rememberAudioManager(): AudioManager

