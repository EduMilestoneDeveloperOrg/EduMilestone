package com.edumilestone.app

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EduMilestoneApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("ExampleApplication", "Application started")
        // Initialize any global dependencies here
    }
}