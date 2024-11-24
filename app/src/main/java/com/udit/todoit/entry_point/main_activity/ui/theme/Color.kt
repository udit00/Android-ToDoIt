package com.udit.todoit.entry_point.main_activity.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// define your colors for dark theme
val clear_dark = Color(0xFFA05162)
val dark_btn = Color(0xFF222427)

// define your colors for dark theme
val light_btn = Color(android.graphics.Color.parseColor("#E9F0F4"))
val light_bg = Color(android.graphics.Color.parseColor("#F6F8F9"))
val clear_light = Color(0xFFF1C8D1)

object TodoStatusColors {
    val colorPending = Color(0xFFFF8400)
    val colorLater = Color(0xD50519A8)
    val colorCompleted = Color(0xFF13B608)
}

object AddTodoTypeColors {
    val closeAlertButton = Color(0xFF804141)
    val floatingActionButton = Color(0xff49634b)
}

sealed class ThemeColors(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val text: Color
)  {
    object Night: ThemeColors(
        background = Color.Black,
        surface = dark_btn,
        primary = clear_dark,
        text = Color.White
    )
    object Day: ThemeColors(
        background = light_bg,
        surface = light_btn,
        primary = clear_light,
        text = Color.Black
    )
}