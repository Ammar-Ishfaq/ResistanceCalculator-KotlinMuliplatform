package com.ammar.resistorassistant

import android.app.Application
import com.ammar.resistorassistant.di.initKoin

class ResistorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
