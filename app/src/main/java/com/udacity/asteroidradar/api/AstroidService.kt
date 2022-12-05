package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AstroidServiceInterface {
    @GET("neo/rest/v1/feed")
    fun getService(
        @Query("api_key") api_key: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Call<String>

}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

object AstroidService {
    val retrofitService: AstroidServiceInterface by lazy {
        retrofit.create(AstroidServiceInterface::class.java)
    }
}