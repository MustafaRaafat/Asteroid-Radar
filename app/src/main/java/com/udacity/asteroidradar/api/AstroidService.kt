package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AstroidServiceInterface {
    @GET("neo/rest/v1/feed")
    suspend fun getService(
        @Query("api_key") api_key: String
    ): String

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

interface ImageOfTheDayInterface {
    @GET("planetary/apod")
    suspend fun getImage(@Query("api_key") api_key: String): PictureOfDay
}

private val retrofit2 = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

object ImageOfTheDayService {
    val retrofit2service: ImageOfTheDayInterface by lazy {
        retrofit2.create(ImageOfTheDayInterface::class.java)
    }
}