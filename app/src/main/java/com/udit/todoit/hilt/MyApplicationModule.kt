package com.udit.todoit.hilt

import android.content.Context
import com.udit.todoit.entry_point.application.MyApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MyApplicationModule {

    @Provides
    fun provideMainApplicationInstance(@ApplicationContext context: Context): MyApp {
        return context as MyApp
    }

}