package com.udit.todoit.ui.add_todo_type

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.udit.todoit.entry_point.main_activity.ui.theme.AddTodoTypeColors
import com.udit.todoit.room.entity.TodoType
import com.udit.todoit.ui.add_todo_type.models.TodoTypeColorModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoType(viewModel: AddTodoTypeViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val showAlertTodoType = viewModel.showTodoType.collectAsStateWithLifecycle()

    var enteredTypeByUser by viewModel.todoTypeTitle
    var selectedColorByUser by viewModel.selectedTodoTypeColor
    val colorList by viewModel.todoTypeColorList

    val todoTypeId by viewModel.todoTypeId.collectAsStateWithLifecycle()

    if(!showAlertTodoType.value) return

    LaunchedEffect("") {
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

//    fun closeAlert() {
//        viewModel.hide()
//    }

    fun upsertTodoType() {
        viewModel.upsertTodoType()
    }

    BasicAlertDialog (
        modifier = Modifier
            .padding(vertical = 100.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
//            .fillMaxSize()
        ,
        onDismissRequest = {
            viewModel.hide()
        },
        content = {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                border = BorderStroke(
                    width = 2.dp,
                    color = selectedColorByUser.color
                )
//                    .height(200.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
//                        .padding(vertical = 20.dp, horizontal = 0.dp)
//                        .background(Color.Red)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
//                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
//                            .background(Color.Red)
                            .fillMaxWidth()
                            .padding(end = 20.dp)
                        ,
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            modifier = Modifier
                                .clip(ShapeDefaults.Large)
                                .background(AddTodoTypeColors.closeAlertButton),
                            onClick = {
                                viewModel.hide()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = ""
                            )
                        }
                    }

                    OutlinedTextField(
//                        modifier = Modifier,
//                            .background(Color.Blue),
                        value = enteredTypeByUser,
                        onValueChange = { value: String ->
                            enteredTypeByUser = value
                        },
                        label = { Text(text = "Todo Type") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Info, contentDescription = "")
                        }
                    )

                    LazyVerticalGrid(
                        modifier = Modifier
//                            .fillMaxSize()
                            .fillMaxWidth()
                            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
//                            .fillMaxHeight()
//                            .fillMaxHeight(0.8f)
//                            .background(Color.Red)
                                ,
                        columns = GridCells.Fixed(count = 3),
//                        contentPadding = PaddingValues(5.dp)
                    ) {
                        itemsIndexed(
                            items = colorList
                        ) { _: Int, colorModel: TodoTypeColorModel ->

//                            Box(
//                                modifier = Modifier
//                                    .size(60.dp)
//                                    .padding(8.dp)
//                                    .background(color, shape = CircleShape)
//                                    .clickable {
//                                        cardColor = color
//                                    },
//                            )
                            Button(
                                modifier = Modifier
//                                    .clip(CircleShape)
                                    .padding(10.dp)
                                ,
                                colors = ButtonColors(
                                    contentColor = colorModel.color,
                                    containerColor = colorModel.color,
                                    disabledContentColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent
                                ),
                                onClick = {
                                    selectedColorByUser = colorModel
                                }
                            ) {

                            }

//                            OutlinedIconButton(
//                                modifier = Modifier,
//                                shape = CircleShape,
//                                colors = IconButtonColors(
//                                    contentColor = Color.Transparent,
//                                    containerColor = color,
//                                    disabledContentColor = Color.Red,
//                                    disabledContainerColor = Color.Red
//                                ),
//                                border = BorderStroke(
//                                    color = color,
//                                    width = Dp(1f)
//                                ),
//                                onClick = {
//
//                                },
//                            ) {
//
//                            }

                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(vertical = 6.dp, horizontal = 10.dp)
//                            .fillMaxHeight(0.4f)
//                            .background(Color.Blue)
                                ,
                        horizontalArrangement = Arrangement.End
                    ) {
//                        IconButton(
//                            onClick = {
//
//                            },
//                            modifier = Modifier,
//                            colors = IconButtonColors(
//                                containerColor = Color.Green,
//                                contentColor = Color.Transparent,
//                                disabledContentColor = Color.Transparent,
//                                disabledContainerColor = Color.Transparent
//                            )
//                        ) {
//                            Text("Add")
//                        }
                        ExtendedFloatingActionButton(
                            onClick = {
                                upsertTodoType()
                            },
//                            containerColor = if(selectedColorByUser.color == Color.Transparent) {
//                                Color.Black
//                            } else {
//                                selectedColorByUser.color
//                            },
                            containerColor = animateColorAsState(
                                targetValue = if(selectedColorByUser.color == Color.Transparent) {
                                    Color.Black
                                } else {
                                    selectedColorByUser.color
                                },
                                animationSpec = tween(
                                    durationMillis = 500,
                                    delayMillis = 500,
                                    easing = EaseIn
                                )
                            ).value
//                            containerColor = AddTodoTypeColors.floatingActionButton,

                        ) {
                            Text(
                                text = if(todoTypeId == 0) {
                                    "Add"
                                } else {
                                    "Update"
                                },
                                modifier = Modifier,
//                                color =  selectedColorByUser
                                color = animateColorAsState(
                                    targetValue = if(selectedColorByUser.color == Color.Transparent || !selectedColorByUser.isLight) {
                                        Color.White
                                    } else {
                                        Color.Black
                                    },
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        delayMillis = 500,
                                        easing = EaseIn
                                    )
                                ).value
//                                color = when(selectedColorByUser.color.value) {
//                                    Color.Yellow.value -> {
//                                        Color.Black
//                                    } Color.Cyan.value -> {
//                                        Color.Black
//                                    } Color.Green.value -> {
//                                        Color.Black
//                                    } else -> {
//                                        Color.White
//                                    }
//                                }
                            )
                        }
                    }


                }
            }
        },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            usePlatformDefaultWidth = true,
            decorFitsSystemWindows = true,
        )
    )

}