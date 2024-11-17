package com.udit.todoit.ui.upsert_todo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.udit.todoit.ui.add_todo_type.AddTodoType
import com.udit.todoit.ui.add_todo_type.AddTodoTypeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertTodoScreen(
    viewModel: UpsertTodoViewModel = hiltViewModel(),
    todoTypeViewModel: AddTodoTypeViewModel = hiltViewModel()
) {

    val todoTypesList = viewModel.todoTypesList.collectAsStateWithLifecycle()
    val showAddTodoType = todoTypeViewModel.showTodoType.collectAsStateWithLifecycle()
//    val isExpanded = viewModel.isTodoTypeDropDownMenuExpanded.collectAsStateWithLifecycle()

//    val datePickerState = rememberDatePickerState()
    val selectedDate = viewModel.targetDatePickerState.selectedDateMillis?.let {
        viewModel.convertMillisToDate(it)
    } ?: ""

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("Todo")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.goBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back",

                            )
                    }
                },
                modifier = Modifier
            )
        },
        bottomBar = {

        }
    ) { innerPadding ->

        if (showAddTodoType.value) {
            AddTodoType(todoTypeViewModel)
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            OutlinedTextField(
                value = viewModel.todoTitle.value,
                onValueChange = {
                    viewModel.todoTitle.value = it
                },
                label = { Text(text = "Todo Title.") },
//                textStyle = TextStyle(brush = brush),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "")
                }
            )

            OutlinedTextField(
                value = viewModel.todoDescription.value,
                onValueChange = {
                    viewModel.todoDescription.value = it
                },
                label = { Text(text = "Todo Description.") },
//                textStyle = TextStyle(brush = brush),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Info, contentDescription = "")
                }
            )

//            OutlinedTextField(
//                value = viewModel.selectedTodoType.value?.typename ?: "Select",
//                onValueChange = {
////                    viewModel.todoDescription.value = it
//                },
//                readOnly = true,
//                label = { Text(text = "Category.") },
////                textStyle = TextStyle(brush = brush),
////                leadingIcon = {
////                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "")
////                },
//                trailingIcon = {
//                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "")
//                },
//
//            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                TextButton(
                    colors = ButtonColors(
                        contentColor = Color.White,
                        containerColor = Color.Black,
                        disabledContentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    ),
                    modifier = Modifier,
                    onClick = {
                        viewModel.isTodoTypeDropDownMenuExpanded.value = true
                    },

                    ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 20.dp),
                        text = viewModel.selectedTodoType.value?.typename ?: "Select",
                        color = if (viewModel.selectedTodoType.value != null) {
                            Color(value = viewModel.selectedTodoType.value!!.color.toULong())
                        } else {
                            Color.White
                        }
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        tint = if (viewModel.selectedTodoType.value != null) {
                            Color(value = viewModel.selectedTodoType.value!!.color.toULong())
                        } else {
                            Color.White
                        },
                        contentDescription = null
                    )
                }

                DropdownMenu(
                    modifier = Modifier,
                    expanded = viewModel.isTodoTypeDropDownMenuExpanded.value,
                    properties = PopupProperties(

                    ),
                    onDismissRequest = {
                        viewModel.isTodoTypeDropDownMenuExpanded.value = false
                    },

                    ) {
                    todoTypesList.value.forEach { entry ->
                        println(entry.typename)
                        DropdownMenuItem(
                            modifier = Modifier,
                            text = {
                                Text(entry.typename)
                            },
                            onClick = {
                                viewModel.selectedTodoType.value = entry
                                viewModel.isTodoTypeDropDownMenuExpanded.value = false
                            },
                        )
                    }
                }
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        todoTypeViewModel.show()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = null
                    )
                }
            }


            OutlinedTextField(
//                value = viewModel.targetDate.value,
                value = selectedDate,
                onValueChange = { date ->
//                    viewModel.targetDate.value = date
                },
                label = { Text("Target") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.showDatePicker.value = !viewModel.showDatePicker.value
                    }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select date"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp, horizontal = 20.dp)
                    .height(64.dp)
            )

            if (viewModel.showDatePicker.value) {
                Popup(
                    onDismissRequest = { viewModel.showDatePicker.value = false },
                    alignment = Alignment.TopStart
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = 64.dp)
                            .shadow(elevation = 4.dp)
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    ) {
                        DatePicker(
                            state = viewModel.targetDatePickerState,
                            showModeToggle = false                            
                        )
                    }
                }
            }
        }
    }
}