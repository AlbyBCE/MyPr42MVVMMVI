package com.example.mypr42mviver3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.mypr42mviver3.data.BirdDao
import com.example.mypr42mviver3.data.BirdEntity
import com.example.mypr42mviver3.data.BirdViewStates
import com.example.mypr42mviver3.data.BirdsRepo
import com.example.mypr42mviver3.data.MyDB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BirdViewModel : ViewModel() {
    private val birdDB: MyDB =
        Room.databaseBuilder(App.instance.applicationContext, MyDB::class.java, name = "BDB")
            .build()
    private val birdDao: BirdDao = birdDB.birds()
    private val repo: BirdsRepo = BirdsRepo(birdDao)

    private val _viewState = MutableStateFlow<BirdViewStates>(BirdViewStates.Loading)
    val viewState: StateFlow<BirdViewStates> = _viewState.asStateFlow()

    fun loadBirds() {
        _viewState.value = BirdViewStates.Loading
        viewModelScope.launch {
            try {
                repo.allBirds.collect { _viewState.value = BirdViewStates.Success(it) }

            } catch (e: Exception) {
                _viewState.value = BirdViewStates.Error("Error Loading")
            }
        }
    }

    fun insertBirds(bird: BirdEntity) {
        viewModelScope.launch {
            repo.insertBird(bird)
        }
    }

    fun deleteBirds(bird: BirdEntity) {
        viewModelScope.launch {
            repo.deleteBird(bird)
        }
    }
}