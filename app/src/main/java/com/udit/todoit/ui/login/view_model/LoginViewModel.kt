package com.udit.todoit.ui.login.view_model

import androidx.lifecycle.ViewModel
import com.udit.todoit.ui.login.model.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository): ViewModel() {

    fun loginUser() {

    }
}