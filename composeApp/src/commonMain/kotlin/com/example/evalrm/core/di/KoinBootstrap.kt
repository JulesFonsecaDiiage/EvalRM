package com.example.evalrm.core.di

import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

fun initKoin(configure: (KoinApplication.() -> Unit)? = null) {
    if (GlobalContext.getOrNull() != null) return

    startKoin {
        configure?.invoke(this)
        modules(appModule)
    }
}

