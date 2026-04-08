package com.example.evalrm.cross.audio

import android.media.MediaActionSound
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.evalrm.cross.context.audioManager

class AndroidAudioManager(
    private val mediaActionSound: MediaActionSound,
) : AudioManager {
    override fun playLocationOpened() {
        mediaActionSound.play(MediaActionSound.SHUTTER_CLICK)
    }
}

@Composable
actual fun rememberAudioManager(): AudioManager {
    val context = LocalContext.current
    return remember(context) { audioManager() }
}

