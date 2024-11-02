package com.udit.todoit.ui.add_todo_type

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
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

    val colorList by remember {
        mutableStateOf(listOf(
            Color.Red,
            Color.Gray,
            Color.Green,
            Color.Red,
            Color.LightGray,
            Color.Cyan,
            Color.Red,
            Color.Gray,
            Color.Green,
            Color.Red,
            Color.LightGray,
            Color.Cyan,
            Color.Red,
            Color.Gray,
            Color.Green,
            Color.Red,
            Color.LightGray,
            Color.Cyan,
        ))
    }


    BasicAlertDialog (
        modifier = Modifier
            .padding(vertical = 100.dp)
            .fillMaxSize()
        ,
        onDismissRequest = {

        },
        content = {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
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
                            .fillMaxHeight(0.8f)
                            .background(Color.Red),
                        columns = GridCells.Fixed(count = 2)
                    ) {

//                        itemsIndexed(
//                            items = colorList,
//
//                        ) {
//                            Box(
//
//                            ) {
//
//                            }
//                        }
//                        IconButton(
//                            colors = Color.
//                        ) {
//
//                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(vertical = 0.dp, horizontal = 10.dp)
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
                            containerColor = AddTodoTypeColors.floatingActionButton,

                        ) {
                            Text(
                                text = "Add",
                                modifier = Modifier,
                                color = Color.White
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