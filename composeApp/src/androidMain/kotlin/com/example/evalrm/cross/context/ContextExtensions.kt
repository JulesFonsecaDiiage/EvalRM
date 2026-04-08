package com.example.evalrm.cross.context

import android.media.MediaActionSound
import com.example.evalrm.cross.audio.AndroidAudioManager
import com.example.evalrm.cross.audio.AudioManager

fun audioManager(): AudioManager {
    val sound = MediaActionSound().apply {
        load(MediaActionSound.SHUTTER_CLICK)
    }
    return AndroidAudioManager(sound)
}

