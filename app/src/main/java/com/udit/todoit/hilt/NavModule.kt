package com.udit.todoit.navigation.nav_provider

import android.content.Context
import androidx.navigation.NavHostController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavModule {

    @Provides
    @Singleton
    fun providesNavProvider(@ApplicationContext context: Context): NavHostController {
        return NavigationProvider(context).navController
    }
}