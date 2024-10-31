package com.udit.todoit.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udit.todoit.room.entity.Todo
import com.udit.todoit.room.entity.TodoType
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    val todoList = viewModel.todos.collectAsStateWithLifecycle()
    val todoTypeList = viewModel.todoTypes.collectAsStateWithLifecycle()
    val progressTemporary by remember { mutableStateOf(0.6f)}

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Home")
                },
                modifier = Modifier
            )
        },
        floatingActionButton = {
            Button(
                onClick = {
                    viewModel.insertTodo()
                }
            ) {
                Text("Add")
            }
        }
//        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
//        modifier = Modifier.background(Color.Gray)
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).background(Color.Gray)
        ) {

            LazyRow (
                modifier = Modifier.padding(10.dp)
            ) {
              itemsIndexed(
                  items = todoTypeList.value,
                  key = {index: Int, item: TodoType ->  item.typeId}
              )  { index, typeItem ->
                  HeaderTypeCard(typeItem, 6, 10)
              }

            }
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

@Composable
fun HeaderTypeCard(typeItem: TodoType, pendingTasksCount: Int, totalTasksCount: Int) {
    val progress by remember { mutableFloatStateOf((pendingTasksCount.toFloat()/totalTasksCount.toFloat()).toFloat()) }
//    val progress by remember { mutableFloatStateOf(0.6f) }
    Card(
        modifier = Modifier
    ) {

        Column(
            modifier = Modifier
                .width(200.dp)
//                              .height(100.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 15.dp, top = 10.dp),
                fontSize = TextUnit(value = 11f, type = TextUnitType.Sp),
//                                  .background(Color.Red),
                text = "${totalTasksCount} Tasks"
            )
            Text(
                modifier = Modifier
                    .padding(start = 15.dp, top = 0.dp),
                fontSize = TextUnit(value = 18f, type = TextUnitType.Sp),
                text = typeItem.typename
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier.padding(end = 15.dp),
                    fontSize = TextUnit(value = 11f, type = TextUnitType.Sp),
                    text = "$pendingTasksCount /$totalTasksCount"
                )
            }
            LinearProgressIndicator(
                modifier = Modifier
                    .height(30.dp)
//                                  .padding(15.dp),
                    .padding(bottom = 10.dp, start = 15.dp, end = 15.dp),
                color = Color.Red,
                trackColor = Color.Gray,
                strokeCap = StrokeCap.Round,
                progress = { progress }
            )
        }
    }
}