package com.udit.todoit.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.udit.todoit.room.entity.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    val todoList = viewModel.todos.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Home")
                },
                modifier = Modifier
            )
        },
//        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
//        modifier = Modifier.background(Color.Gray)
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).background(Color.Gray)
        ) {
//            Text(text = "HomeScreen")
//            Button(onClick = {
//                viewModel.getTodos()
//            }) {
//                Text(text = "Get Todos")
//            }
            LazyColumn(
                modifier = Modifier
//                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxSize()
            ) {

                itemsIndexed(
                    items = todoList.value,
                    key = {index: Int, item: Todo ->  item.todoID}
                ) { index, todoItem ->

                    Card(
                        modifier = Modifier
//                            .background(Color.Gray)
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(text = todoItem.title)
                    }
                }
            }
        }
    }
}