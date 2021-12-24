package com.quere.moodra

import android.app.Application
import com.quere.moodra.di.LocalDataSourceModule
import com.quere.moodra.di.NetworkModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        NetworkModule
        LocalDataSourceModule
    }
}