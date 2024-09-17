package com.example.demo_one_a

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
//         Inisialisasi yang diperlukan
    }
}