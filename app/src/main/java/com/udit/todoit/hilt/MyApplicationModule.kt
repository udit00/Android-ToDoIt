package com.udit.todoit.hilt

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udit.todoit.api.Api
import com.udit.todoit.entry_point.application.MyApp
import com.udit.todoit.room.TodoDatabase
import com.udit.todoit.shared_preferences.StorageHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyApplicationModule {

    @Provides
    @Singleton
    fun provideMainApplicationInstance(@ApplicationContext context: Context): MyApp {
        return context as MyApp
    }

    @Provides
    @Singleton
    fun provideApiPadhaiApi(myApp: MyApp): Api {
        return Api(myApp)
    }

    @Provides
    @Singleton
    fun providesStorageHelper(myApp: MyApp): StorageHelper {
        return StorageHelper(myApp)
    }

    @Provides
    @Singleton
    fun providesTodoDatabase(myApp: MyApp): TodoDatabase {
        return Room.databaseBuilder(myApp.applicationContext, klass = TodoDatabase::class.java, name = "todo_db").build()
    }

}