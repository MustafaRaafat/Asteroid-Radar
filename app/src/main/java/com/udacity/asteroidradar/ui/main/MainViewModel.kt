package com.udacity.asteroidradar.ui.main

import android.app.Application
import android.content.Context
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
            repo.refreshData()
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

    //    private val mutapleList = MutableLiveData<List<Asteroid>>()
//    val mutapleImage = MutableLiveData<PictureOfDay>()
//    val astroidLiveData: MutableLiveData<List<Asteroid>> = mutapleList
//    val ImageLiveData: LiveData<PictureOfDay> = mutapleImage
//    fun getdata(context: Context) {
//
//        viewModelScope.launch {
//            val Astroid = parseAsteroidsJsonResult(
//                JSONObject(
//                    AstroidService.retrofitService.getService(Constants.api_key).await()
//                )
//            )
//            for (asteroid in Astroid) {
//                getDatabase(context).dao.insertAstroid(asteroid)
//            }
//        }
//        viewModelScope.launch {
////            mutapleList.postValue(getDatabase(context).dao.getAstroidFromDB().value)
//
//        }
//    }
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("faild")
        }
    }
}