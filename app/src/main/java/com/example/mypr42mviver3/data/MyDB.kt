package com.example.mypr42mviver3.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BirdEntity::class],
    version = 1
)
abstract class MyDB : RoomDatabase() {
    abstract fun birds(): BirdDao
}