package com.udit.todoit.entry_point.main_activity.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    @Serializable
    data object LoginPage: Screen(NavigationRoutes.LoginScreenRoute)
    @Serializable
    data object HomePage: Screen(NavigationRoutes.HomeScreenRoute)
    @Serializable
    data object UpsertTodoPage: Screen(NavigationRoutes.UpsertTodoScreenRoute)
}