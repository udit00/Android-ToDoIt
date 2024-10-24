package com.udit.todoit.ui.common_composables

//import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


@Composable
fun GradientButtonWithLoader(
    text: String,
    gradient: Brush,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            AnimatedContent(
                targetState = isLoading,
                modifier = Modifier,
                transitionSpec = {
                    slideInHorizontally() togetherWith slideOutHorizontally()

                }
            ) { showLoading ->
                if (showLoading) {
                    CircularProgressIndicator(color = Color.Gray)
                } else {
                    Text(text = text)
                }
            }
        }

    }
}