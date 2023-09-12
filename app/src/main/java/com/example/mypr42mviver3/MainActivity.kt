package com.example.mypr42mviver3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.mypr42mviver3.data.BirdIntent
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(vm: BirdViewModel = viewModel()) {
    var viewStates by remember { mutableStateOf<BirdViewStates>(BirdViewStates.Loading) }
    var isAddingBird by remember { mutableStateOf(false) }
    var newBirdName by remember { mutableStateOf("") }
    var newBirdAge by remember { mutableStateOf("") }

    // Отправляем интент для загрузки данных
    LaunchedEffect(Unit) {
        vm.processIntent(BirdIntent.LoadBirds)


        // Наблюдаем за изменениями состояния во ViewModel
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
                    Row(
                        modifier = Modifier.clickable {
                            // Создайте интент для удаления птицы и отправьте его во ViewModel
                            val removeIntent = BirdIntent.RemoveBird(bird)
                            vm.processIntent(removeIntent)
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = bird.toString())
                        Spacer(modifier = Modifier.width(8.dp))
                        // Добавьте кнопку или иконку для удаления элемента
                        IconButton(
                            onClick = {
                                // Создайте интент для удаления птицы и отправьте его во ViewModel
                                val removeIntent = BirdIntent.RemoveBird(bird)
                                vm.processIntent(removeIntent)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
                    }
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

    // Показать форму для добавления элемента
    if (isAddingBird) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = newBirdName,
                onValueChange = { newBirdName = it },
                label = { Text("Name") }
            )
            OutlinedTextField(
                value = newBirdAge,
                onValueChange = { newBirdAge = it },
                label = { Text("Age") }
            )
            Button(
                onClick = {
                    // Создать интент для добавления новой птицы и отправить его во ViewModel
                    val addIntent = BirdIntent.AddBird(
                        BirdEntity(
                            name = newBirdName,
                            age = newBirdAge.toIntOrNull() ?: 0
                        )
                    )
                    vm.processIntent(addIntent)

                    // Сбросить состояние формы
                    isAddingBird = false
                    newBirdName = ""
                    newBirdAge = ""
                },
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Text("Add Bird")
            }
        }
    }

    // Кнопка для отображения формы для добавления элемента
    Button(
        onClick = {
            isAddingBird = true
        },
        modifier = Modifier.padding(16.dp).fillMaxWidth()
    ) {
        Text("Add Bird")
    }
}