package com.udit.todoit.hilt

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.udit.todoit.navigation.nav_provider.NavigationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavModule {
//    @Provides
//    @Singleton
//    fun providesNavController(navigationProvider: NavigationProvider): NavController {
//        return navigationProvider.navController
//    }

    @Provides
    @Singleton
    fun providesNavProvider(@ApplicationContext context: Context): NavigationProvider {
        return NavigationProvider(context)
    }

}