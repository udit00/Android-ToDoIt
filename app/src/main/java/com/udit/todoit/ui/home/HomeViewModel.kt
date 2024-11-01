package com.udit.todoit.ui.home

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.udit.todoit.base.BaseViewModel
import com.udit.todoit.room.TodoDatabase
import com.udit.todoit.room.entity.Todo
import com.udit.todoit.room.entity.TodoType
import com.udit.todoit.ui.add_todo_type.AddTodoType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    BaseViewModel() {

    private val _todos: MutableStateFlow<ArrayList<Todo>> = MutableStateFlow(arrayListOf())
    val todos get() = _todos.asStateFlow()

    private val _todoTypes: MutableStateFlow<List<TodoType>> = MutableStateFlow(arrayListOf())
    val todoTypes get() = _todoTypes.asStateFlow()

    private val _showAddTodoTypeAlert: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showAddTodoTypeAlert get() = _showAddTodoTypeAlert

    init {
//        insertTodoType("Home")
        getTodoTypesFromRoomDb()
        getTodosFromRoomDB()

//        viewModelScope.launch {
//            setObservers()
//        }
//        getTodos()
    }

    init {
//        val todoType = TodoType(typename = "GROCERY")
//        viewModelScope.launch {
//            todoDb.todoTypeDao.upsertTodoType(todoType)
//        }
//        val todo = Todo( createId = 1, title = "test", description = "testing", createdOn = LocalDateTime.now().toString(), target = LocalDateTime.now().toString(), todoTypeID = 1)
//        viewModelScope.launch {
//            todoDb.todoDao.upsertTodo(todo)
//        }
    }

    fun showAddTodoTypeAlert() {
        _showAddTodoTypeAlert.value = true
    }

    fun hideAddTodoTypeAlert() {
        _showAddTodoTypeAlert.value = false
    }

    fun getTodos(searchValue: String? = "") {
        val searchedString: String = if (searchValue.isNullOrBlank()) "" else searchValue
        val params = mapOf(
            "userId" to "1",
            "charStr" to searchedString
        )
        viewModelScope.launch {
            homeRepository.getTodos(params) { jsonObject: JSONObject ->
                try {
                    val apiResponse = handleApiResponse(jsonObject)
                    if (apiResponse != null) {
                        val typeToken = object : TypeToken<ArrayList<Todo>>() {}.type
                        val todoList =
                            Gson().fromJson<ArrayList<Todo>>(apiResponse.Response, typeToken)
                        _todos.value = todoList
                        Log.d(this@HomeViewModel.javaClass.simpleName, todoList.toString())
                    }
                } catch (ex: Exception) {
                    _errorMutableFlow.value = ex.message
                }
            }
        }
    }

    fun getTodosFromRoomDB(searchValue: String? = "") {
        viewModelScope.launch {
            homeRepository.getTodosFromRoomDb { list ->
                _todos.value = list
            }
        }
    }

    fun getTodoTypesFromRoomDb() {
        viewModelScope.launch {
            homeRepository.getTodoTypesFromRoomDb {
//                _todoTypes.value = it
                val types: MutableList<TodoType> = it.toMutableList()
                //
                _todoTypes.value = types
//                types.add()
            }
        }
    }

    fun insertTodo() {
        viewModelScope.launch {
//            if (_todoTypes.value.isEmpty()) {
//                insertTodoType("Home")
//                delay(100)
//            }
            val todo: Todo = Todo(
                title = "Grocery",
                description = "Get the Tomato",
                todoTypeID = 1,
                target = LocalDateTime.now().toString(),
                createdOn = LocalDateTime.now().toString(),
                createId = 1
            )
            homeRepository.upsertTodo(todo)
        }
    }

    fun insertTodoType(typeName: String, color: Color) {
        val todoType = TodoType(typename = typeName, color = color.toString())
        viewModelScope.launch {
            homeRepository.upsertTodoType(todoType = todoType)
        }
    }

//    fun openAddTodoTypeAlert() {
////        val state by remember
//        AddTodoType()
//    }
}