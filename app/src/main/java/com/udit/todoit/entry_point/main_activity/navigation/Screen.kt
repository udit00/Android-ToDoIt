package com.udit.todoit.entry_point.main_activity.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    @Serializable
    data object LoginPage: Screen(NavigationRoutes.LoginScreenRoute)
    @Serializable
    data object HomePage: Screen(NavigationRoutes.HomeScreenRoute)

    @Serializable
    data class UpsertTodoPage(val todoId: Int = -1) : Screen(NavigationRoutes.UpsertTodoScreenRoute)
}