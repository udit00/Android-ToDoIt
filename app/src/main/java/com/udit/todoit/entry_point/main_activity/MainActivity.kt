package com.udit.todoit.entry_point.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.udit.todoit.ui.login.LoginScreen
import com.udit.todoit.entry_point.main_activity.navigation.Screen
import com.udit.todoit.entry_point.main_activity.ui.theme.ToDoItTheme
import com.udit.todoit.navigation.nav_provider.NavigationProvider
import com.udit.todoit.ui.home.HomeScreen
import com.udit.todoit.ui.upsert_todo.UpsertTodoScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {

    @Inject lateinit var navigationProvider: NavigationProvider
//    @Inject lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoItTheme {
                MainScreen(navigationProvider.navController)
            }
        }
    }
}

@Composable
fun MainScreen (
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