package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
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
        val con = Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true).setRequiresCharging(true).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()
        val rebete =
            PeriodicWorkRequestBuilder<RefreshApp>(1, TimeUnit.DAYS).setConstraints(con).build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshApp.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            rebete
        )
    }
}