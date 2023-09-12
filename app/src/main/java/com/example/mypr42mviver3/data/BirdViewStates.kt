package com.example.mypr42mviver3.data

sealed class BirdViewStates {
    object Loading : BirdViewStates()
    data class Success(val birds: List<BirdEntity>) : BirdViewStates()
    data class Error(val message: String) : BirdViewStates()
}
sealed class BirdIntent {
    data class AddBird(val bird: BirdEntity) : BirdIntent()
    data class RemoveBird(val bird: BirdEntity) : BirdIntent()
    object LoadBirds : BirdIntent()
}
