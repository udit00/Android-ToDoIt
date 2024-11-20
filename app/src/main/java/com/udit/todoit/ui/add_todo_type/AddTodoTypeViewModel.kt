package com.udit.todoit.ui.add_todo_type

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udit.todoit.base.BaseViewModel
import com.udit.todoit.room.entity.Todo
import com.udit.todoit.room.entity.TodoType
import com.udit.todoit.ui.add_todo_type.models.TodoTypeColorModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTodoTypeViewModel @Inject constructor(private val repository: AddTodoTypeRepository) :
    BaseViewModel() {

    val todoTypeColorList: State<List<TodoTypeColorModel>> = mutableStateOf(
        listOf(
            TodoTypeColorModel(color = Color.Red, isLight = false),
            TodoTypeColorModel(color = Color.Cyan, isLight = true),
            TodoTypeColorModel(color = Color.Green, isLight = true),
            TodoTypeColorModel(color = Color.Blue, isLight = true),
            TodoTypeColorModel(color = Color.Magenta, isLight = false),
            TodoTypeColorModel(color = Color.Yellow, isLight = true),
        )
    )

    private val _todoTypeId: MutableStateFlow<Int> = MutableStateFlow(0)
    val todoTypeId get() = _todoTypeId.asStateFlow()

    private val _showTodoType: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showTodoType get() = _showTodoType.asStateFlow()

    val defaultTodoTypeColor = TodoTypeColorModel(color = Color.Transparent, isLight = false)

    val todoTypeTitle = mutableStateOf("")
    val selectedTodoTypeColor = mutableStateOf(defaultTodoTypeColor)

    val isSaved = MutableSharedFlow<Boolean>()


    fun resetAlert() {
        _todoTypeId.value = 0
        todoTypeTitle.value = ""
        selectedTodoTypeColor.value = defaultTodoTypeColor
    }

    fun upsertTodoType() {
        if (todoTypeTitle.value.isBlank()) {
            notifyUserAboutError("Title cannot be empty.")
            return
        } else if (selectedTodoTypeColor.value == defaultTodoTypeColor) {
            notifyUserAboutError("Select a color.")
            return
        }
        val todoType = TodoType(
            typename = todoTypeTitle.value,
            isLight = selectedTodoTypeColor.value.isLight,
            color = selectedTodoTypeColor.value.color.value.toString(),
            typeId = todoTypeId.value
        )
        viewModelScope.launch {
            repository.ifTodoTypeExists(todoType.typename.trim(), { exists ->
                if (exists) {
                    notifyUserAboutError("Todo Type Already Exists.")
                } else {
                    viewModelScope.launch {
                        repository.upsertTodoType(todoType = todoType)
                        hide()
                        isSaved.emit(true)
                    }
                }
            })
        }
    }

    private fun getTodoTypeDetails() {
        viewModelScope.launch {
            repository.getTodoTypeDetails(
                todoTypeId = _todoTypeId.value
            ).collectLatest { todoType: TodoType? ->
                if (todoType != null) {
                    Log.d("TODO_TYPE", todoType.toString())
                    todoTypeTitle.value = todoType.typename
                    selectedTodoTypeColor.value = TodoTypeColorModel(
                        color = Color(value = todoType.color.toULong()),
                        isLight = todoType.isLight
                    )
                }
            }
        }
    }

    fun show(todoTypeId: Int = 0) {
        _todoTypeId.value = todoTypeId
        if (todoTypeId != 0) {
            getTodoTypeDetails()
        }
        _showTodoType.value = true
    }

    fun hide() {
        resetAlert()
        _showTodoType.value = false
    }

    fun ifTodoTypeAlreadyExists(todoTypeName: String) {
        viewModelScope.launch {
//            repository.ifTodoTypeExists(todoTypeName).collectLatest { exists ->
//                notifyUserAboutError(exists.toString())
//            }
            repository.ifTodoTypeExists(todoTypeName, { doesExists: Boolean ->

            })
        }
    }
}