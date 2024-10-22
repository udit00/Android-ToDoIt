package com.udit.todoit.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.udit.todoit.R
import com.udit.todoit.ui.common_composables.GradientButton

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {

    Scaffold(
        modifier = Modifier,
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally


//                .background(Color.Cyan)
        ) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.CenterHorizontally)
//                    .background(Color.Red)
//                    .background(color = Color(red = 184, green = 206, blue = 210, alpha = 255))

            ) {
                Image(
                    painter = painterResource(id = R.drawable.todoit_logo),  // Reference your WebP image here (image.webp)
                    contentDescription = "To-DoIt Logo",
                    modifier = Modifier
                        .border(
                            border = BorderStroke(0.9.dp, Color.Black),
                            shape = RoundedCornerShape(20.dp)
                        )
//                        .padding(10.dp)
//                        .width(50.dp)
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(20.dp))
//                        .fillMaxSize()
//                        .width(50.dp)
//                    .fillMaxWidth()
//                    .fillMaxHeight()
                    ,  // Optional modifier for sizing
                    contentScale = ContentScale.Fit,  // Optional: adjust how the image should be scaled within its bounds
                    colorFilter = null  // Optional: apply a color filter if needed
                )
            }


//            Text(text = "Login Screen")
            val brush = remember {
                Brush.linearGradient(
                    colors = listOf(Color.Red, Color.Blue, Color.Green)
                )
            }
            val buttonGradient = remember {
                Brush.linearGradient(
                    colors = listOf(
                        Color(
                            red = 57,
                            green = 182,
                            blue = 158,
                            alpha = 255
                        ),
                        Color(
                            red = 61,
                            green = 190,
                            blue = 253,
                            alpha = 255
                        )
                    )
                )
            }
            OutlinedTextField(
                value = viewModel.userNameMobileNo.value,
                onValueChange = {
                    viewModel.userNameMobileNo.value = it
                },
                label = { Text(text = "User Name / Mobile No.") },
                textStyle = TextStyle(brush = brush)
            )
            OutlinedTextField(
                value = viewModel.passWord.value,
                onValueChange = {
                    viewModel.passWord.value = it
                },
                label = { Text(text = "Password") },
                textStyle = TextStyle(brush = brush),

                )
            Spacer(modifier = Modifier.height(40.dp))
            GradientButton(
                text = "Login",
                gradient = buttonGradient,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(70.dp, 12.dp),
                onClick = {
                    viewModel.loginUser("uditnair90", "123456")
                })

        }
    }
}