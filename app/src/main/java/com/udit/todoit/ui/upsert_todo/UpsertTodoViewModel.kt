package com.udit.todoit.ui.upsert_todo

import android.util.Log
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
import com.udit.todoit.shared_preferences.StorageHelper
import com.udit.todoit.ui.login.model.LoginModel
import com.udit.todoit.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class UpsertTodoViewModel @Inject constructor(
    private val repository: UpsertTodoRepository,
    private val storageHelper: StorageHelper,
    private val navigationProvider: NavigationProvider
): BaseViewModel() {

    val todoTitleError = mutableStateOf(false)
    val todoTitle = mutableStateOf("")
    val todoTitleMaxChar = 100

    val todoDescriptionError = mutableStateOf(false)
    val todoDescription = mutableStateOf("")


    val selectedTodoType: MutableState<TodoType?> = mutableStateOf(null)
    private val _todoTypesList: MutableStateFlow<List<TodoType>> = MutableStateFlow(listOf())
    val todoTypesList get() = _todoTypesList
    
    val showDatePicker = mutableStateOf(false)
    @OptIn(ExperimentalMaterial3Api::class)
    val targetDatePickerState = DatePickerState(
        locale = Locale.getDefault(),
        initialSelectedDateMillis = System.currentTimeMillis()
    )
//    @OptIn(ExperimentalMaterial3Api::class)
//    val targetDate = mutableStateOf(
//    targetDatePickerState.selectedDateMillis?.let { convertMillisToDate(it) } ?: "Test"
//    )

    val isTodoTypeDropDownMenuExpanded = mutableStateOf(false)

    private val _userData: MutableStateFlow<LoginModel?> = MutableStateFlow(null)
    val userData get() = _userData

    init {
        viewModelScope.launch {
            repository.errorFlow.collectLatest { errMsg ->
                notifyUserAboutError(errMsg.toString())
            }
        }
        getUserData()
        getTypesList()
    }

    fun getTypesList() {
        viewModelScope.launch {
            repository.getTypesForList { listOfTodoTypes ->
                _todoTypesList.value = listOfTodoTypes
                if (listOfTodoTypes.isNotEmpty()) {
                    selectedTodoType.value = listOfTodoTypes[0]
                }
            }
        }
    }

    private fun getUserData() {
        val loginData = storageHelper.getLoginData()
        _userData.value = loginData
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

    @OptIn(ExperimentalMaterial3Api::class)
    fun upsertTodo() {
        viewModelScope.launch {
            if(todoTitle.value.isBlank()) {
                notifyUserAboutError("Title cannot be empty.")
                todoTitleError.value = true
                return@launch
            } else if(todoDescription.value.isBlank()) {
                notifyUserAboutError("Description cannot be empty.")
                return@launch
            } else if(selectedTodoType.value == null) {
                notifyUserAboutError("Select Todo Type.")
                return@launch
            } else if(targetDatePickerState.selectedDateMillis == null || targetDatePickerState.selectedDateMillis.toString().isBlank()) {
                notifyUserAboutError("Select a Target Date.")
                return@launch
            } else if(userData.value == null) {
                notifyUserAboutError("Something went wrong, please try again.")
                return@launch
            }
            val createdOnDateTime: String = Utils.getCurrentDateTime().toString()
            val targetDateTime: String = Utils.getDateTimeStringFromLong(targetDatePickerState.selectedDateMillis!!).toString()
            logger(createdOnDateTime)
            logger(targetDateTime)
            val todo = Todo(
                title = todoTitle.value,
                description = todoDescription.value,
                todoTypeID = selectedTodoType.value!!.typeId,
                todoID = 0,
                createdOn = createdOnDateTime,
                target = targetDateTime,
                createId = userData.value!!.UserID,
                todoCompletionStatusID = 1
            )
            repository.ifTodoAlreadyExists(todo) { ifExists ->
                if(ifExists) {
                    notifyUserAboutError("Todo already exists.")
                } else {
                    viewModelScope.launch {
                        repository.upsertTodo(
                            todo = todo
                        )
                    }
                    goBack()
                }
            }
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