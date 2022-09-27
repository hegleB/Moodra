package com.quere.presenation.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        private lateinit var application : App
        fun getInstace() : App = application
    }

    override fun onCreate() {
        super.onCreate()

        application = this
    }

}