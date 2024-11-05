package com.udit.todoit.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.udit.todoit.base.BaseViewModel
import com.udit.todoit.entry_point.main_activity.navigation.Screen
import com.udit.todoit.navigation.nav_provider.NavigationProvider
import com.udit.todoit.room.TodoDatabase
import com.udit.todoit.room.entity.Todo
import com.udit.todoit.room.entity.TodoType
import com.udit.todoit.ui.add_todo_type.AddTodoType
import com.udit.todoit.ui.add_todo_type.models.TodoTypeColorModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val navigationProvider: NavigationProvider
) : BaseViewModel() {

    @Inject lateinit var roomDB: TodoDatabase

    private val _todos: MutableStateFlow<ArrayList<Todo>> = MutableStateFlow(arrayListOf())
    val todos get() = _todos.asStateFlow()

    private val _todoTypes: MutableStateFlow<List<TodoType>> = MutableStateFlow(arrayListOf())
    val todoTypes get() = _todoTypes.asStateFlow()

    private val _showAddTodoTypeAlert: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showAddTodoTypeAlert get() = _showAddTodoTypeAlert

    fun showAddTodoTypeAlert() {
        _showAddTodoTypeAlert.value = true
    }

    fun hideAddTodoTypeAlert() {
        _showAddTodoTypeAlert.value = false
    }



    init {
        viewModelScope.launch {
            homeRepository.errorFlow.collectLatest { errMsg ->
                notifyUserAboutError(errMsg)
//                hideLoading()
            }
        }
        getTodoTypesFromRoomDb()
        getTodosFromRoomDB()
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
                    notifyUserAboutError(ex.message)
                }
            }
        }
    }

    private fun getTodosFromRoomDB(searchValue: String? = "") {
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

    fun navigateToUpsertTodoScreen(todoId: Int? = -1) {
        navigationProvider.navController.navigate(Screen.UpsertTodoPage)
    }



//    fun openAddTodoTypeAlert() {
////        val state by remember
//        AddTodoType()
//    }
}