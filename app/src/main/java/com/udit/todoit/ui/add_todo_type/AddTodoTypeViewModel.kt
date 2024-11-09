package com.udit.todoit.ui.add_todo_type

import androidx.lifecycle.viewModelScope
import com.udit.todoit.base.BaseViewModel
import com.udit.todoit.room.entity.TodoType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTodoTypeViewModel @Inject constructor(private val repository: AddTodoTypeRepository): BaseViewModel() {

    private val _showTodoType: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showTodoType get() = _showTodoType.asStateFlow()

    fun upsertTodoType(todoType: TodoType) {
        viewModelScope.launch {
            repository.upsertTodoType(todoType = todoType)
        }
    }

    fun show() {
        _showTodoType.value = true
    }

    fun hide() {
        _showTodoType.value = false
    }
}