package com.udit.todoit.ui.add_todo_type

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udit.todoit.base.BaseViewModel
import com.udit.todoit.room.entity.TodoType
import com.udit.todoit.ui.add_todo_type.models.TodoTypeColorModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddTodoTypeViewModel(private val repository: AddTodoTypeRepository){

    val enteredNameByUser: MutableStateFlow<String> = MutableStateFlow("")

    private val _selectedColorByUser: MutableStateFlow<TodoTypeColorModel> = MutableStateFlow(
        TodoTypeColorModel(
            color = Color.Transparent,
            isLight = true
        )
    )
    val selectedColorByUser get() = _selectedColorByUser

    val closeAlert: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val addTodoTypeColorList: MutableStateFlow<List<TodoTypeColorModel>> =  MutableStateFlow(listOf(
        TodoTypeColorModel(color = Color.Red, isLight = false),
        TodoTypeColorModel(color = Color.Cyan, isLight = true),
        TodoTypeColorModel(color = Color.Green, isLight = false),
        TodoTypeColorModel(color = Color.Blue, isLight = true),
        TodoTypeColorModel(color = Color.Magenta, isLight =  false),
        TodoTypeColorModel(color = Color.Yellow, isLight = false),
    ))



    suspend fun insertTodoType() {
        val typeName = enteredNameByUser.value
        val color = selectedColorByUser.value.color
        if(typeName.isBlank()) {
//            notifyUserAboutError("Type name cannot be empty.")
            return
        } else if(color.value == Color.Transparent.value) {
//            notifyUserAboutError("Select a color for your type - ${typeName}.")
            return
        }
        val todoType = TodoType(typename = typeName, color = color.toString())
        withContext(Dispatchers.IO) {
            repository.upsertTodoType(todoType = todoType)
            closeAlert.value = true
        }
    }

//    override fun onCleared() {
//        Log.d("MyViewModel", "onCleared: ")
//    }

}