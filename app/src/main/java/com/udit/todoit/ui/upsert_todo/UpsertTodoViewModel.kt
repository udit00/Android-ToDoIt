package com.udit.todoit.ui.upsert_todo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.udit.todoit.base.BaseViewModel
import com.udit.todoit.room.entity.TodoType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpsertTodoViewModel @Inject constructor(
    private val repository: UpsertTodoRepository
): BaseViewModel() {

    val todoTitle = mutableStateOf("")
    val todoDescription = mutableStateOf("")
    val selectedTodoType: MutableState<TodoType?> = mutableStateOf(null)
    private val _todoTypesList: MutableStateFlow<List<TodoType>> = MutableStateFlow(listOf())
    val todoTypesList get() = _todoTypesList


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

}