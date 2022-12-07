package com.udacity.asteroidradar.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AstroidService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.asDomainModel
import com.udacity.asteroidradar.room.AsteroidDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepo(private var database: AsteroidDb) {

    val asteroidRebo: LiveData<List<Asteroid>> =
        Transformations.map(database.dao.getAstroidFromDB()){
            it.asDomainModel()
        }

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            val listAst = AstroidService.retrofitService.getService(Constants.api_key)
            val Ast = parseAsteroidsJsonResult(JSONObject(listAst))
            for (astroid in Ast){
                database.dao.insertAstroid(astroid)
            }
        }

    }
}