package com.example.evalrm

import android.app.Application
import com.example.evalrm.core.di.initKoin
import org.koin.android.ext.koin.androidContext

class EvalRmApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@EvalRmApplication)
        }
    }
}

