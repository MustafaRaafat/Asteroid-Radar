package com.udacity.asteroidradar

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.udacity.asteroidradar.work.RefreshApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication : Application() {
    private val appScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        delay()
    }

    private fun delay() {
        appScope.launch {
            setTime()
        }
    }

    private fun setTime() {
        val rebete = PeriodicWorkRequestBuilder<RefreshApp>(1, TimeUnit.DAYS).build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshApp.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            rebete
        )
    }
}