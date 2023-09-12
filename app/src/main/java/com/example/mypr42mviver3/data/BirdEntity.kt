package com.example.mypr42mviver3.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BirdEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val age: Int
)