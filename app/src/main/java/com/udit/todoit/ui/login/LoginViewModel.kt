package com.udit.todoit.ui.login.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udit.todoit.ui.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository): ViewModel() {

    fun loginUser(mobile: String, password: String) {
        val params: MutableMap<String, String> = mapOf(
            "prm_mobileno" to mobile,
            "prm_password" to password,
            "prm_useripaddress" to "",
            "prm_platform" to "ANDROID"
        ).toMutableMap()
        viewModelScope.launch (Dispatchers.IO) {
            loginRepository.userLogin(params)
        }
    }
}