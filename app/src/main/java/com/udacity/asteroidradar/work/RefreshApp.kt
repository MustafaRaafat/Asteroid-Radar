package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.repo.AsteroidRepo
import com.udacity.asteroidradar.room.getDatabase
import retrofit2.HttpException

class RefreshApp(appContext: Context, par: WorkerParameters) : CoroutineWorker(appContext, par) {
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repo = AsteroidRepo(database)


        return try {
            repo.refreshData()
            Result.success()
        } catch (ex: HttpException) {
            Result.retry()
        }

    }

    companion object {
        const val WORK_NAME = "RefreshAPP"
    }
}