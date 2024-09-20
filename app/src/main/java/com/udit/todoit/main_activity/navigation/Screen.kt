package com.udit.todoit.main_activity.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    @Serializable
    object LoginPage: Screen(NavigationRoutes.LoginScreenRoute)
    @Serializable
    object HomePage: Screen(NavigationRoutes.HomeScreenRoute)
}