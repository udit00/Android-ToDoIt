package com.udit.todoit.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewModelScope
import com.udit.todoit.base.BaseViewModel
import com.udit.todoit.entry_point.main_activity.navigation.Screen
import com.udit.todoit.entry_point.main_activity.ui.theme.TodoStatusColors
import com.udit.todoit.navigation.nav_provider.NavigationProvider
import com.udit.todoit.room.TodoDatabase
import com.udit.todoit.room.entity.Todo
import com.udit.todoit.room.entity.TodoStatus
import com.udit.todoit.room.entity.TodoType
import com.udit.todoit.shared_preferences.StorageHelper
import com.udit.todoit.ui.add_todo_type.models.TodoTypeCount
import com.udit.todoit.ui.add_todo_type.models.TodoTypeView
import com.udit.todoit.ui.home.model.TodoView
import com.udit.todoit.ui.login.model.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

//enum class FILTERBY {
//    PENDING,
//    COMPLETED,
//    LATER
//}

sealed class FilterBy(
    val id: Int,
    val name: String,
    val icon: ImageVector,
    val color: Color,
    val isLight: Boolean
) {
    data object PENDING : FilterBy(
        id = 1,
        name = "Pending",
        icon = Icons.Filled.DateRange,
        color = TodoStatusColors.colorPending,
        isLight = true
    )

    data object COMPLETED : FilterBy(
        id = 2,
        name = "Completed",
        icon = Icons.Filled.CheckCircle,
        color = TodoStatusColors.colorCompleted,
        isLight = false
    )

    data object LATER : FilterBy(
        id = 3,
        name = "Later",
        icon = Icons.Outlined.Lock,
        color = TodoStatusColors.colorLater,
        isLight = false
    )
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val storageHelper: StorageHelper,
    private val navigationProvider: NavigationProvider
) : BaseViewModel() {

    @Inject
    lateinit var roomDB: TodoDatabase

    private val _todos: MutableStateFlow<ArrayList<TodoView>> = MutableStateFlow(arrayListOf())
    val todos get() = _todos.asStateFlow()

    private val _selectedTodoType: MutableStateFlow<TodoTypeView?> = MutableStateFlow(null)
    val selectedTodoType get() = _selectedTodoType.asStateFlow()

    private val _todoTypes: MutableStateFlow<List<TodoTypeView>> = MutableStateFlow(arrayListOf())
    val todoTypes get() = _todoTypes.asStateFlow()

    private val _todoStatusList: MutableStateFlow<List<TodoStatus>> =
        MutableStateFlow(arrayListOf())
    val todoStatusList get() = _todoStatusList.asStateFlow()

    private val _showAddTodoTypeAlert: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showAddTodoTypeAlert get() = _showAddTodoTypeAlert.asStateFlow()

    private val _userData: MutableStateFlow<LoginModel?> = MutableStateFlow(null)
    val userData get() = _userData.asStateFlow()

    private val _filterByList: MutableStateFlow<List<FilterBy>> = MutableStateFlow(listOf())
    val filterByList get() = _filterByList.asStateFlow()

    private val _selectedFilterBy: MutableStateFlow<FilterBy> = MutableStateFlow(FilterBy.PENDING)
    val selectedFilterBy get() = _selectedFilterBy.asStateFlow()

    fun showAddTodoTypeAlert() {
        _showAddTodoTypeAlert.value = true
    }

    fun hideAddTodoTypeAlert() {
        _showAddTodoTypeAlert.value = false
    }

    fun changeFilter(filterBy: FilterBy) {
        _selectedFilterBy.value = filterBy
        getTodosFromRoomDB(
            todoTypeId = selectedTodoType.value?.todoTypeId?:0,
            todoStatusId = filterBy.id
        )
    }

    init {
        viewModelScope.launch {
            homeRepository.errorFlow.collectLatest { errMsg ->
                notifyUserAboutError(errMsg)
//                hideLoading()
            }
        }
        getFilterByList()
        getUserData()
        getTodoTypesFromRoomDb()
        getTodoStatusFromRoomDb()
        getTodosFromRoomDB(
            todoTypeId = selectedTodoType.value?.todoTypeId ?: 0,
            todoStatusId = selectedFilterBy.value.id
        )
    }

//    fun getTodoTypeCount(todoTypeId: Int, response: (todoTypeCount: TodoTypeCount) -> Unit) {
//        viewModelScope.launch {
//            homeRepository.getTodoCountFromRoomDb(todoTypeId = todoTypeId, { todoTypeCount ->
//                response(todoTypeCount)
//            })
//        }
//    }

    private fun getFilterByList() {
        var filterByTempList: List<FilterBy> = listOf(
            FilterBy.PENDING,
            FilterBy.COMPLETED,
            FilterBy.LATER
        )
//        repeat(3) {
//            filterBy = FilterBy.PENDING
//        }
        _filterByList.value = filterByTempList
    }

    private fun getUserData() {
        val loginData = storageHelper.getLoginData()
        _userData.value = loginData
    }


//    fun getTodos(searchValue: String? = "") {
//        val searchedString: String = if (searchValue.isNullOrBlank()) "" else searchValue
//        val params = mapOf(
//            "userId" to "1",
//            "charStr" to searchedString
//        )
//        viewModelScope.launch {
//            homeRepository.getTodos(params) { jsonObject: JSONObject ->
//                try {
//                    val apiResponse = handleApiResponse(jsonObject)
//                    if (apiResponse != null) {
//                        val typeToken = object : TypeToken<ArrayList<Todo>>() {}.type
//                        val todoList =
//                            Gson().fromJson<ArrayList<Todo>>(apiResponse.Response, typeToken)
//                        _todos.value = todoList
//                        Log.d(this@HomeViewModel.javaClass.simpleName, todoList.toString())
//                    }
//                } catch (ex: Exception) {
//                    notifyUserAboutError(ex.message)
//                }
//            }
//        }
//    }

    private fun getTodosFromRoomDB(todoTypeId: Int, todoStatusId: Int, searchValue: String = "") {
        _todos.value = arrayListOf()
        viewModelScope.launch {
            if (todoTypeId == 0) {
                homeRepository.getTodosFromRoomDb { list ->
//                _todos.value = list
                    _todos.value = list

                }
            } else {
                homeRepository.getTodosFromRoomDbFilterByTodoTypeId(todoTypeId = todoTypeId, todoStatusId = todoStatusId) { list ->
                    _todos.value = list
                }
            }
        }
    }

    private fun getTodoTypesFromRoomDb() {
        viewModelScope.launch {
            homeRepository.getTodoTypesViewFromRoomDb { todoTypes ->
                val types: MutableList<TodoTypeView> = todoTypes.toMutableList()
                _todoTypes.value = types
                if (selectedTodoType.value == null && types.isNotEmpty()) {
                    _selectedTodoType.value = _todoTypes.value[0]
                }
            }
//            homeRepository.getTodoTypesFromRoomDb {
//                val types: MutableList<TodoType> = it.toMutableList()
//                _todoTypes.value = types
//                if (selectedTodoType.value == null && types.isNotEmpty()) {
//                    _selectedTodoType.value = _todoTypes.value[0]
//                }
//            }
        }
    }

    fun filterTodosByTodoType(todoType: TodoTypeView) {
        _selectedTodoType.value = todoType
        getTodosFromRoomDB(todoTypeId = todoType.todoTypeId, todoStatusId = selectedFilterBy.value.id)
//        val statusId: Int = selectedFilterBy.value.id
//        viewModelScope.launch {
//            val typeId = todoType?.typeId ?: 0
//            getTodosFromRoomDB(
//                searchValue = "",
//                todoTypeId = typeId,
//                todoStatusId = statusId
//            )
//        }
    }

    private fun getTodoStatusFromRoomDb() {
        viewModelScope.launch {
            homeRepository.getAllTodoStatusFromRoomDb {
                _todoStatusList.value = it
            }
        }
    }

    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("EEE, MMM d HH:mm aaa", Locale.getDefault())
        return formatter.format(Date(millis))
    }


//    fun insertTodo() {
//        viewModelScope.launch {
////            if (_todoTypes.value.isEmpty()) {
////                insertTodoType("Home")
////                delay(100)
////            }
//            val todo: Todo = Todo(
//                title = "Grocery",
//                description = "Get the Tomato",
//                todoTypeID = 1,
//                target = LocalDateTime.now().toString(),
//                createdOn = LocalDateTime.now().toString(),
//                createId = 1
//            )
//            homeRepository.upsertTodo(todo)
//        }
//    }

    fun updateTodo(todoView: TodoView) {
        val todo = Todo(
            todoID = todoView.todoID,
            title = todoView.title,
            description = todoView.description,
            target = todoView.target,
            createdOn = todoView.createdOn,
            todoTypeID = todoView.todoTypeID,
            createId = todoView.createId,
            todoCompletionStatusID = todoView.todoCompletionStatusID
        )
        viewModelScope.launch {
            val todoId = homeRepository.upsertTodo(todo)
            getTodoTypesFromRoomDb()
        }
    }

    fun navigateToUpsertTodoScreen(todoId: Int = -1) {
//        val navigationExtras: Navigator
        navigationProvider.navController.navigate(Screen.UpsertTodoPage(todoId = todoId))
    }


    fun logOut() {
        val isRemoved = storageHelper.remove(StorageHelper.LOGIN_PREF_TAG)
        if (isRemoved) {
            navigationProvider.navController.navigate(Screen.LoginPage) {
                popUpTo(0)
            }
        } else {
            notifyUserAboutError("Something went wrong.")
        }
    }

//    fun openAddTodoTypeAlert() {
////        val state by remember
//        AddTodoType()
//    }
}