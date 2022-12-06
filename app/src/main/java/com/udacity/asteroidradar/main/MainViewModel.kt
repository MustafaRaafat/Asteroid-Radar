package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AstroidService
import com.udacity.asteroidradar.api.ImageOfTheDayService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.room.getDatabase
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val mutapleList = MutableLiveData<List<Asteroid>>()
    val mutapleImage = MutableLiveData<PictureOfDay>()
    val astroidLiveData: MutableLiveData<List<Asteroid>> = mutapleList
    val ImageLiveData: LiveData<PictureOfDay> = mutapleImage
    fun getdata(context: Context) {

        AstroidService.retrofitService.getService(Constants.api_key)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val root = JSONObject(response.body())
                    var astroidList = parseAsteroidsJsonResult(root)
//                    mutapleList.value = astroidList
                    viewModelScope.launch {
                        for (asteroid in astroidList) {
                            getDatabase(context).dao.insertAstroid(asteroid)
                        }
                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                }
            })
        viewModelScope.launch {
            mutapleList.postValue(getDatabase(context).dao.getAstroidFromDB())
            mutapleImage.postValue(ImageOfTheDayService.retrofit2service.getImage(Constants.api_key))
        }
    }
}