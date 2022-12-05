package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AstroidService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val mutapleList = MutableLiveData<List<Asteroid>>()
    val astroidLiveData: LiveData<List<Asteroid>> get() = mutapleList
    fun getdata() {
        AstroidService.retrofitService.getService(Constants.api_key, "2015-09-08", "2015-09-08")
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val root = JSONObject(response.body())
                    var astroidList = parseAsteroidsJsonResult(root)
                    mutapleList.value = parseAsteroidsJsonResult(root)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                }
            })
    }
}