package com.udit.todoit.ui.upsert_todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertTodoScreen(viewModel: UpsertTodoViewModel = hiltViewModel()) {

    val todoTypesList = viewModel.todoTypesList.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("Todo")
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go Back"
                    )
                },
                modifier = Modifier
            )
        },
        bottomBar = {

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
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

            ExposedDropdownMenuBox(
                modifier = Modifier,
                expanded = viewModel.isTodoTypeDropDownMenuExpanded.value,
                onExpandedChange = {

                },
            ) {
                todoTypesList.value.forEach { entry ->
                    DropdownMenuItem(
                        modifier = Modifier,
                        text = {
                            Text(entry.typename)
                        },
                        onClick = {
                            viewModel.selectedTodoType.value = entry
                        },
                    )
                }
            }
        }
    }
}