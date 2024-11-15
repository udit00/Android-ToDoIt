package com.udit.todoit.ui.upsert_todo

import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udit.todoit.base.BaseViewModel
import com.udit.todoit.navigation.nav_provider.NavigationProvider
import com.udit.todoit.room.entity.Todo
import com.udit.todoit.room.entity.TodoType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class UpsertTodoViewModel @Inject constructor(
    private val repository: UpsertTodoRepository,
    private val navigationProvider: NavigationProvider
): BaseViewModel() {

    val todoTitle = mutableStateOf("")
    val todoDescription = mutableStateOf("")
    val selectedTodoType: MutableState<TodoType?> = mutableStateOf(null)
    private val _todoTypesList: MutableStateFlow<List<TodoType>> = MutableStateFlow(listOf())
    val todoTypesList get() = _todoTypesList
    
    val showDatePicker = mutableStateOf(false)
    @OptIn(ExperimentalMaterial3Api::class)
    val targetDatePickerState = DatePickerState(
        locale = Locale.getDefault()
    )
//    @OptIn(ExperimentalMaterial3Api::class)
//    val targetDate = mutableStateOf(
//    targetDatePickerState.selectedDateMillis?.let { convertMillisToDate(it) } ?: "Test"
//    )

    val isTodoTypeDropDownMenuExpanded = mutableStateOf(false)

    init {
        viewModelScope.launch {
            repository.getTypesForList { listOfTodoTypes ->
                _todoTypesList.value = listOfTodoTypes
                if(listOfTodoTypes.isNotEmpty()) {
                    selectedTodoType.value = listOfTodoTypes[0]
                }
            }
        }
    }

//    private fun validateAndSave(todoId: Int = 0) {
//        if(todoTitle.value.isBlank()) {
//            notifyUserAboutError("Title cannot be empty.")
//        } else if(todoDescription.value.isBlank()) {
//            notifyUserAboutError("Description cannot be empty.")
//        } else if(selectedTodoType.value == null) {
//            notifyUserAboutError("No Type was selected. You could create a new one by clicking on the '+' button.")
//        } else if(targetDate.value.isBlank()) {
//            notifyUserAboutError("Target cannot be empty.")
//        } else {
//            val localDateTime: LocalDateTime = LocalDateTime.now()
//            val todo = Todo(
//                todoID = todoId,
//                todoTypeID = selectedTodoType.value!!.typeId,
//                createId = 1,
//                title = todoTitle.value,
//                description = todoDescription.value,
//                createdOn = localDateTime.toString(),
//                target = targetDate.value
//
//            )
//            upsertTodo(todo)
//        }
//    }

    private fun upsertTodo(todo: Todo) {
        viewModelScope.launch {
            repository.upsertTodo(
                todo = todo
            )
        }
    }

    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("EEE, MMM d HH:mm aaa", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    fun goBack() {
        navigationProvider.navController.popBackStack()
    }
}