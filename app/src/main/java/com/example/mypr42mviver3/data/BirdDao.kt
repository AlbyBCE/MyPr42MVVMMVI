package com.example.mypr42mviver3.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BirdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBird(bird: BirdEntity)

    @Delete
    suspend fun deleteBird(bird: BirdEntity)

    @Query("SELECT * FROM BirdEntity")
    fun getAllBirds(): Flow<List<BirdEntity>>
}