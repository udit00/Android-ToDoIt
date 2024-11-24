package com.udit.todoit.entry_point.main_activity

import android.os.Bundle
import android.provider.Settings.Global
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.udit.todoit.ui.login.LoginScreen
import com.udit.todoit.entry_point.main_activity.navigation.Screen
import com.udit.todoit.entry_point.main_activity.ui.theme.ToDoItTheme
import com.udit.todoit.entry_point.main_activity.ui.theme.TodoStatusColors
import com.udit.todoit.navigation.nav_provider.NavigationProvider
import com.udit.todoit.room.TodoDatabase
import com.udit.todoit.room.entity.TodoStatus
import com.udit.todoit.ui.home.HomeScreen
import com.udit.todoit.ui.upsert_todo.UpsertTodoScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {

    @Inject
    lateinit var navigationProvider: NavigationProvider

    @Inject
    lateinit var todoDatabase: TodoDatabase
//    @Inject lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//         GlobalScope.launch {
//            todoDatabase.todoStatusDao.getCountOfStatus().collectLatest { count ->
//                if(count <= 0) {
//
//                }
//            }
//        }
        enableEdgeToEdge()
        setContent {
            val scope = rememberCoroutineScope()
            LaunchedEffect(key1 = "") {

                todoDatabase.todoStatusDao.getCountOfStatus().collectLatest { count ->
                    if (count <= 0) {
                        val todoStatus: List<TodoStatus> = listOf(
                            TodoStatus(
                                statusName = "Pending",
                                statusColor = TodoStatusColors.colorPending.value.toString(),
                                isColorLight = true
                            ),
                            TodoStatus(
                                statusName = "Completed",
                                statusColor = TodoStatusColors.colorCompleted.value.toString(),
                                isColorLight = false
                            ),
                            TodoStatus(
                                statusName = "Later",
                                statusColor = TodoStatusColors.colorLater.value.toString(),
                                isColorLight = false
                            )
                        )
                        todoDatabase.todoStatusDao.upsertTodoStatus(todoStatus)
//                        todoStatus.forEach { status ->
//                            todoDatabase.todoStatusDao.upsertTodoStatus(status)
//                        }
                    }
                }
            }
            ToDoItTheme {
                MainScreen(navigationProvider.navController)
            }
        }
    }
}

@Composable
fun MainScreen(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Screen.LoginPage) {
        composable<Screen.LoginPage> {
            LoginScreen()
        }
        composable<Screen.HomePage> {
            HomeScreen()
        }
        composable<Screen.UpsertTodoPage> {
            UpsertTodoScreen()
        }

    }
}