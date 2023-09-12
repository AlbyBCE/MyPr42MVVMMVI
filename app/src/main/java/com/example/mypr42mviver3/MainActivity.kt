package com.example.mypr42mviver3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mypr42mviver3.data.BirdEntity
import com.example.mypr42mviver3.data.BirdViewStates
import com.example.mypr42mviver3.ui.theme.MyPr42MVIVer3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPr42MVIVer3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(vm: BirdViewModel = viewModel()) {
    var viewStates by remember { mutableStateOf<BirdViewStates>(BirdViewStates.Loading) }

    LaunchedEffect(Unit) {

        vm.loadBirds()
        vm.viewState.collect { ns ->
            viewStates = ns
        }
    }
    when (viewStates) {
        is BirdViewStates.Loading -> {
            // Отобразить индикатор загрузки
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is BirdViewStates.Success -> {
            // Отобразить список с данными
            LazyColumn {
                items((viewStates as BirdViewStates.Success).birds) { bird ->
                    Text(text = bird.toString(), modifier = )
                }
            }
        }

        is BirdViewStates.Error -> {
            // Отобразить сообщение об ошибке
            Text(
                text = "Error: ${(viewStates as BirdViewStates.Error).message}",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
