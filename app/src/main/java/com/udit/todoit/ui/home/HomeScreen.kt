package com.udit.todoit.ui.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udit.todoit.room.entity.Todo
import com.udit.todoit.room.entity.TodoType
import com.udit.todoit.ui.add_todo_type.AddTodoType
import com.udit.todoit.ui.common_composables.CardText
import com.udit.todoit.ui.common_composables.CardTextWithText

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val todoList = viewModel.todos.collectAsStateWithLifecycle()
    val todoTypeList = viewModel.todoTypes.collectAsStateWithLifecycle()
    val progressTemporary by remember { mutableStateOf(0.6f) }

    val showAddTodoTypeAlert = viewModel.showAddTodoTypeAlert.collectAsStateWithLifecycle()
    val colorRed = Color.Red

    viewModel.logger(colorRed.toString())

    LaunchedEffect(key1 = "") {
        scope.launch(Dispatchers.Main) {
            viewModel.errorFlow.collectLatest { errMsg: String? ->
                Log.d("TOAST", errMsg?:"HIT")
                errMsg?.let {
//                    Utils.showToast(context, it)
//                    Log.d("TOAST", errMsg)
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


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

        if(showAddTodoTypeAlert.value) {
            AddTodoType(homeViewModel = viewModel)
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.Gray)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp)
                    .background(
                        color = Color(
                            red = 203,
                            green = 203,
                            blue = 203,
                            alpha = 123
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 10.dp)
//                        .background(Color.Red)
                        .fillMaxWidth(),
//                        .background(Color.Red),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CardText(
                        text = "Categories"
                    )
                    IconButton(
                        modifier = Modifier
                            .padding(end = 0.dp),
//                            .offset(x = 0.dp, y = -20.dp),
                        onClick = {
//                            viewModel.openAddTodoTypeAlert()
//                            AddTodoType()
                            viewModel.showAddTodoTypeAlert()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "a",
                            modifier = Modifier
//                                .drawBehind {
//                                    drawCircle(
//                                        color = Color.Green,
//                                        radius = this.size.maxDimension
//                                    )
//                                },
                        )
                    }
                }
                LazyRow(
                    modifier = Modifier
                        .padding(top = 0.dp, start = 5.dp, end = 5.dp)
                        .fillMaxWidth()
//                        .background(Color.Red)
                ) {
                    itemsIndexed(
                        items = todoTypeList.value,
                        key = { index: Int, item: TodoType -> item.typeId }
                    ) { index, typeItem ->
                        HeaderTypeCard(typeItem, 6, 10)
                    }

                }


            }
//            HorizontalDivider(
//                thickness = 2.dp,
//                color = Color.Black,
//                modifier = Modifier
////                    .padding(vertical = 0.dp, horizontal = 10.dp),
//            )
            LazyColumn(
                modifier = Modifier
//                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxSize()
                    .padding(top = 0.dp)
            ) {

                itemsIndexed(
                    items = todoList.value,
                    key = { index: Int, item: Todo -> item.todoID }
                ) { index, todoItem ->
                    TodoCard(todoItem)
                }
            }

        }
    }
}

@Composable
fun HeaderTypeCard(typeItem: TodoType, pendingTasksCount: Int, totalTasksCount: Int) {
    val progress by remember { mutableFloatStateOf((pendingTasksCount.toFloat() / totalTasksCount.toFloat()).toFloat()) }
//    val progress by remember { mutableFloatStateOf(0.6f) }
    Card(
        modifier = Modifier
            .padding(10.dp),
        border = BorderStroke(
            width = 2.dp,
            color = Color.Red
        )
    ) {

        Column(
            modifier = Modifier
                .width(150.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
//                            Color.Gray,
//                            Color.Blue,
                            Color.LightGray,
                            Color.Gray,
                            Color.LightGray
                        )
                    )
                ),
//                              .height(100.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 15.dp, top = 10.dp),
                fontSize = TextUnit(value = 11f, type = TextUnitType.Sp),
//                                  .background(Color.Red),
                text = "${totalTasksCount} Tasks"
            )

            CardTextWithText(
                cardColors = CardColors(
                    containerColor = Color.Red,
                    contentColor = Color.Green,
                    disabledContentColor = Color.Blue,
                    disabledContainerColor = Color.Green
                ),
                cardModifier = Modifier
                    .padding(start = 10.dp),
                textComposable = {
                    Text(
                        fontSize = TextUnit(value = 18f, type = TextUnitType.Sp),
                        text = typeItem.typename,
                        modifier = Modifier
                            .padding(vertical = 0.dp, horizontal = 10.dp),
                        color = Color.White
                    )
                }

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

@Composable
fun TodoCard(todoItem: Todo) {
    Card(
        modifier = Modifier
//                            .background(Color.Gray)
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 0.dp)
    ) {

        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            Text(
                text = todoItem.title,
            )
            Text(
                text = todoItem.description,
                fontSize = TextUnit(value = 11f, type = TextUnitType.Sp),
                modifier = Modifier.padding(start = 10.dp)

            )
        }

    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            modifier = Modifier
                .padding(end = 20.dp)
                .offset(x = 0.dp, y = -20.dp),
            onClick = {

            }
        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "a",
                modifier = Modifier
                    .drawBehind {
                        drawCircle(
                            color = Color.Green,
                            radius = this.size.maxDimension
                        )
                    },
            )
        }
    }
}
