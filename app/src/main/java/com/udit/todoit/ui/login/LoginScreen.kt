package com.udit.todoit.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {

    Scaffold(
        modifier = Modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)

        ) {
            Text(text = "Login Screen")
            Button(onClick = {
                viewModel.loginUser("7011490531", "456789")
            }) {
                Text(text = "Login")
            }

        }       
    }
}