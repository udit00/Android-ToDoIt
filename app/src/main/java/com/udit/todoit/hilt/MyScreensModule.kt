package com.udit.todoit.hilt

import com.udit.todoit.api.Api
import com.udit.todoit.ui.home.HomeRepository
import com.udit.todoit.ui.login.model.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object MyScreensModule {

    @Provides
    @ViewModelScoped
    fun providesLoginRepository(api: Api): LoginRepository {
        return LoginRepository(api)
    }

    @Provides
    @ViewModelScoped
    fun providesHomeRepository(api: Api): HomeRepository {
        return HomeRepository(api)
    }


}