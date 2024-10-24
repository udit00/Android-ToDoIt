package com.udit.todoit.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.udit.todoit.ui.home.data.Todo

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    val todoList = viewModel.todos.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(text = "HomeScreen")
            Button(onClick = {
                viewModel.getTodos()
            }) {
                Text(text = "Get Todos")
            }
            LazyColumn {
                itemsIndexed(
                    items = todoList.value,
                    key = {index: Int, item: Todo ->  item.todoid},
                ) { index, todoItem ->
                    Text(text = todoItem.title)
                }
            }
        }
    }
}