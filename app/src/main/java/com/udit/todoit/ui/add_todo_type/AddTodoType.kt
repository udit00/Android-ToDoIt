package com.udit.todoit.ui.add_todo_type

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.udit.todoit.entry_point.main_activity.ui.theme.AddTodoTypeColors
import com.udit.todoit.ui.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoType() {

    var todoName by remember {
        mutableStateOf("")
    }

    var cardColor by remember {
        mutableStateOf(Color.Black)
    }

    val colorList by remember {
        mutableStateOf(listOf(
            Color.Red,
            Color.Cyan,
            Color.Green,
            Color.Blue,
            Color.Magenta,
            Color.Yellow,
        ))
    }


    BasicAlertDialog (
        modifier = Modifier
            .padding(vertical = 100.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
//            .fillMaxSize()
        ,
        onDismissRequest = {

        },
        content = {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                border = BorderStroke(
                    width = 2.dp,
                    color = cardColor
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

                    OutlinedTextField(
                        value = todoName,
                        onValueChange = {
                            todoName = it
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
                            colorList
                        ) { index, color: Color ->

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
                                    contentColor = color,
                                    containerColor = color,
                                    disabledContentColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent
                                ),
                                onClick = {
                                    cardColor = color
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

                            },
                            containerColor = cardColor,
//                            containerColor = AddTodoTypeColors.floatingActionButton,

                        ) {
                            Text(
                                text = "Add",
                                modifier = Modifier,
                                color = when(cardColor.value) {
                                    Color.Yellow.value -> {
                                        Color.Black
                                    } Color.Cyan.value -> {
                                        Color.Black
                                    } Color.Green.value -> {
                                        Color.Black
                                    } else -> {
                                        Color.White
                                    }
                                }
//                                color = if(cardColor.value == Color.Yellow.value) {
//                                    Color.Black
//                                } else {
//                                    Color.White
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