package com.udit.todoit.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.udit.todoit.ui.login.LoginScreen
import com.udit.todoit.main_activity.navigation.Screen
import com.udit.todoit.main_activity.ui.theme.ToDoItTheme
import com.udit.todoit.ui.home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoItTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomePage) {
        composable<Screen.LoginPage> {
            LoginScreen(navController = navController)
        }
        composable<Screen.HomePage> {
            HomeScreen(navController = navController)
        }
        
    }
}