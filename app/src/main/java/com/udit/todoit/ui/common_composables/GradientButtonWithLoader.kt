package com.udit.todoit.ui.common_composables

//import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOut
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp


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

        AnimatedContent(
            targetState = isLoading,
            modifier = Modifier,
            transitionSpec = {
//                slideIntoContainer(
//                    animationSpec = tween(500, easing = EaseIn),
//                    towards = AnimatedContentTransitionScope.SlideDirection.Start
//                ) togetherWith
                slideIn(
                    animationSpec = tween(500),
                    initialOffset = {
                        IntOffset(
                            x = 0, y = 0
                        )
                    }
                ) togetherWith
//                slideOut (
//                    animationSpec = tween(500),
//                    targetOffset = {
//                        IntOffset(
//                            x = 100, y = 0
//                        )
//                    }
//                )
                fadeOut(
                    animationSpec = tween(500)
                )
            }, label = ""
        ) { showLoading ->
            if (showLoading) {
                CircularProgressIndicator(
                    color = Color(
                        red = 61,
                        green = 190,
                        blue = 253,
                        alpha = 255
                    ),
                    strokeWidth = 5.dp,
                    strokeCap = StrokeCap.Round,
//                    trackColor = Color.Red
                )
            } else {
                Box(
                    modifier = Modifier
                        .background(gradient)
                        .then(modifier),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = text)
                }
            }
        }


    }
}