package com.example.evalrm.cross.audio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.awt.Toolkit

class DesktopAudioManager : AudioManager {
    override fun playLocationOpened() {
        Toolkit.getDefaultToolkit().beep()
    }
}

@Composable
actual fun rememberAudioManager(): AudioManager {
    return remember { DesktopAudioManager() }
}

