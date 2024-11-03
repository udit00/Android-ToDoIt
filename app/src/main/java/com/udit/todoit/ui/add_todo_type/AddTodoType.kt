package com.udit.todoit.ui.add_todo_type

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.udit.todoit.entry_point.main_activity.ui.theme.AddTodoTypeColors
import com.udit.todoit.ui.add_todo_type.models.TodoTypeColorModel
import com.udit.todoit.ui.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoType(homeViewModel: HomeViewModel) {

    val showAlertTodoType = homeViewModel.showAddTodoTypeAlert.collectAsStateWithLifecycle()

    val enteredTypeByUser = homeViewModel.enteredNameByUser.collectAsStateWithLifecycle()

    var selectedColorByUser = homeViewModel.selectedColorByUser.collectAsStateWithLifecycle()
    val colorList = homeViewModel.addTodoTypeColorList.collectAsStateWithLifecycle()

    if(!showAlertTodoType.value) return

    BasicAlertDialog (
        modifier = Modifier
            .padding(vertical = 100.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
//            .fillMaxSize()
        ,
        onDismissRequest = {
            homeViewModel.hideAddTodoTypeAlert()
        },
        content = {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                border = BorderStroke(
                    width = 2.dp,
                    color = selectedColorByUser.value.color
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
                                homeViewModel.hideAddTodoTypeAlert()
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
                        value = enteredTypeByUser.value,
                        onValueChange = { value: String ->
                            homeViewModel.enteredNameByUser.value = value
                        },
                        label = { Text(text = "Enter Todo Type") },
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
                            items = colorList.value
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
                                    homeViewModel.selectedColorByUser.value = colorModel
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
                                homeViewModel.insertTodoType()
                            },
//                            containerColor = if(selectedColorByUser.color == Color.Transparent) {
//                                Color.Black
//                            } else {
//                                selectedColorByUser.color
//                            },
                            containerColor = animateColorAsState(
                                targetValue = if(selectedColorByUser.value.color == Color.Transparent) {
                                    Color.Black
                                } else {
                                    selectedColorByUser.value.color
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
                                text = "Add",
                                modifier = Modifier,
//                                color =  selectedColorByUser
                                color = animateColorAsState(
                                    targetValue = if(selectedColorByUser.value.color == Color.Transparent || !selectedColorByUser.value.isLight) {
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
//            securePolicy = SecureFlagPolicy.SecureOn
        )
    )
}