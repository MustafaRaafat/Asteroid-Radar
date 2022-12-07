package com.udacity.asteroidradar.ui.main

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.udacity.asteroidradar.AsteroidApplication
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.api.AstroidService
import com.udacity.asteroidradar.api.ImageOfTheDayService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.model.asDomainModel
import com.udacity.asteroidradar.repo.AsteroidRepo
import com.udacity.asteroidradar.room.getDatabase
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repo = AsteroidRepo(database)

    init {
        refreshDataFromNetWork()
    }


    private fun refreshDataFromNetWork() {
        viewModelScope.launch {
            try {
                repo.refreshData()
            } catch (ex: Exception) {
                Toast.makeText(getApplication(), "no internet", Toast.LENGTH_SHORT).show()
            }
//            mutapleImage.postValue(ImageOfTheDayService.retrofit2service.getImage(Constants.api_key))
//            val astroidList = parseAsteroidsJsonResult(
//                JSONObject(
//                    AstroidService.retrofitService.getService(Constants.api_key).await()
//                )
//            )
//            mutapleList.postValue(astroidList.asDomainModel())
        }
    }

    val astroidData = repo.asteroidRebo

    private val todayMuta = MutableLiveData<List<Asteroid>>()
    fun getToday(today: String): LiveData<List<Asteroid>> {
        viewModelScope.launch {
            try {
                val Astroid = parseAsteroidsJsonResult(
                    JSONObject(
                        AstroidService.retrofitService.getTodayService(
                            Constants.api_key,
                            today,
                            today
                        )
                    )
                )
                todayMuta.value = Astroid
            } catch (ex: Exception) {
                Toast.makeText(getApplication(), "no internet", Toast.LENGTH_SHORT).show()
            }
        }
        return todayMuta
    }

    private val mutapleImage = MutableLiveData<PictureOfDay>()

    //    val astroidLiveData: MutableLiveData<List<Asteroid>> = mutapleList
//    val ImageLiveData: LiveData<PictureOfDay> = mutapleImage
    fun getImageOfTheDay(): LiveData<PictureOfDay> {
        viewModelScope.launch {
            try {
                mutapleImage.value =
                    ImageOfTheDayService.retrofit2service.getImage(Constants.api_key)

            } catch (ex: Exception) {
                Toast.makeText(getApplication(), "no Internet", Toast.LENGTH_SHORT)
            }
        }
        return mutapleImage
    }
}