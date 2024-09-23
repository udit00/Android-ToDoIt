package com.udit.todoit.entry_point.main_activity.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    @Serializable
    object LoginPage: Screen(NavigationRoutes.LoginScreenRoute)
    @Serializable
    object HomePage: Screen(NavigationRoutes.HomeScreenRoute)
}