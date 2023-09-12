package com.example.mypr42mviver3.data

import kotlinx.coroutines.flow.Flow

class BirdsRepo(private val dao: BirdDao) {
    val allBirds: Flow<List<BirdEntity>> = dao.getAllBirds()

    suspend fun insertBird(bird: BirdEntity) {
        dao.insertBird(bird)
    }

    suspend fun deleteBird(bird: BirdEntity) {
        dao.deleteBird(bird)
    }
}