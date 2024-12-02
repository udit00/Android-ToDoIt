package com.udit.todoit.ui.upsert_todo

import android.app.TimePickerDialog
import android.widget.Button
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.udit.todoit.R
import com.udit.todoit.entry_point.main_activity.ui.theme.TodoCardColors
import com.udit.todoit.entry_point.main_activity.ui.theme.UpsertTodoColors
import com.udit.todoit.ui.add_todo_type.AddTodoType
import com.udit.todoit.ui.add_todo_type.AddTodoTypeViewModel
import com.udit.todoit.ui.common_composables.GradientButton
import kotlinx.coroutines.flow.collectLatest
import java.time.format.DateTimeFormatter
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertTodoScreen(
    todoId: Int,
    viewModel: UpsertTodoViewModel = hiltViewModel(),
    todoTypeViewModel: AddTodoTypeViewModel = hiltViewModel()
) {


    val context = LocalContext.current
    val todoTypesList = viewModel.todoTypesList.collectAsStateWithLifecycle()
    val showAddTodoType = todoTypeViewModel.showTodoType.collectAsStateWithLifecycle()
//    val isExpanded = viewModel.isTodoTypeDropDownMenuExpanded.collectAsStateWithLifecycle()

//    val datePickerState = rememberDatePickerState()
    val selectedDate = viewModel.targetDatePickerState.selectedDateMillis?.let {
        viewModel.convertMillisToDate(it)
    } ?: ""

    val selectedTime = viewModel.targetTimePickerState.let {
        viewModel.convertTimeToView(it)
    } ?: ""

    LaunchedEffect(key1 = "") {
//        viewModel.errorFlow.collectLatest {
        viewModel.setTodoId(todoId = todoId)
        viewModel.errorFlow.collectLatest { errMsg: String? ->
            errMsg?.let {
//                    Utils.showToast(context, it)
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
        todoTypeViewModel.isSaved.collectLatest {
            viewModel.getTypesList()
        }
//        }
    }

    Scaffold(
        modifier = Modifier.padding(),
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
            BottomAppBar(
                modifier = Modifier,
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding()
                        .fillMaxWidth(),
//                        .background(color = Color.Red)
                    onClick = {
                        viewModel.upsertTodo()
                    },
                    shape = RectangleShape,
                ) {
                    Text("Save")
                }
            }

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
                    if (viewModel.todoTitle.value.length < viewModel.todoTitleMaxChar) viewModel.todoTitle.value =
                        it
                    viewModel.todoTitleError.value = viewModel.todoTitle.value.isBlank()
                },
                label = { Text(text = "Todo Title.") },
//                textStyle = TextStyle(brush = brush),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "")
                },
                maxLines = 2,
                minLines = 1,
                supportingText = {
                    Text(
                        text = "${viewModel.todoTitle.value.length} / ${viewModel.todoTitleMaxChar}",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                },
                isError = viewModel.todoTitleError.value,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words
                ),

                )

            OutlinedTextField(
                value = viewModel.todoDescription.value,
                onValueChange = {
                    viewModel.todoDescription.value = it
                    viewModel.todoDescriptionError.value = viewModel.todoDescription.value.isBlank()
                },
                label = { Text(text = "Todo Description.") },
//                textStyle = TextStyle(brush = brush),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Info, contentDescription = "")
                },
                minLines = 4,
                isError = viewModel.todoDescriptionError.value,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words
                )
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
//                value = viewModel.targetDate.value,
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(0.4f),
                    value = selectedDate,
                    onValueChange = { date ->
//                    viewModel.targetDate.value = date
                    },
                    label = { Text("Date") },
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
                )

                OutlinedTextField(
//                value = viewModel.targetDate.value,
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(0.3f),
                    value = selectedTime,
                    onValueChange = { time ->
//                    viewModel.targetDate.value = date
                    },
                    label = { Text("Time") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.showTimePicker.value = !viewModel.showTimePicker.value
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.icon_clock),
                                contentDescription = "Select Time"
                            )
                        }
                    },
                )
            }

            if (viewModel.showDatePicker.value) {
                DatePickerDialog(
                    onDismissRequest = { viewModel.showDatePicker.value = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.showDatePicker.value = false
                            }
                        ) {
                            Text("Set")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                viewModel.showDatePicker.value = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    },
                ) {
                    DatePicker(
                        state = viewModel.targetDatePickerState,
                        modifier = Modifier,
                    )
                }

            }

            if (viewModel.showTimePicker.value) {
                Dialog(
                    onDismissRequest = {

                    },
                ) {
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TimePicker(
                            state = viewModel.targetTimePickerState
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
//                            GradientButton(
//                                text = "Cancel",
//                                modifier = Modifier.padding(10.dp),
//                                onClick = {
//                                    viewModel.showTimePicker.value = false
//                                },
//                                gradient = TodoCardColors.editButtonGradient
//                            )
                            GradientButton(
                                text = "Confirm",
                                modifier = Modifier.padding(20.dp),
                                onClick = {
                                    viewModel.showTimePicker.value = false
                                },
                                gradient = UpsertTodoColors.confirmBtnGradient
                            )
                        }
                    }
                }
//                Column {
//                    TimePicker(
//                        state = viewModel.targetTimePickerState,
//                    )
//                    Button(
//                        onClick = {
//                            viewModel.showTimePicker.value = false
//                        }
//                    ) {
//                        Text("Close")
//                    }
//                    Button(
//                        onClick = {
//                            viewModel.showTimePicker.value = false
//                        }
//                    ) {
//                        Text("Confirm")
//                    }
//                }
            }
//                TimePicker(
//                    state = viewModel.targetTimePickerState
//                )
        }
    }

}