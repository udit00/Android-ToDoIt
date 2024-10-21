package com.udit.todoit.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.udit.todoit.api.data.apipadhai.ApiPadhaiResponse
import com.udit.todoit.base.BaseViewModel
import com.udit.todoit.ui.login.model.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository): BaseViewModel() {

    private val _loginMutableFlow: MutableStateFlow<LoginModel?> = MutableStateFlow(null)
    val loginSuccessful get() = _loginMutableFlow.asStateFlow().drop(1)

    fun loginUser(userNameMobileNo: String?, password: String?) {
        if(userNameMobileNo.isNullOrBlank()) {
            _errorMutableFlow.value = "UserName cannot be empty."
            return
        } else if(password.isNullOrBlank()) {
            _errorMutableFlow.value = "Password cannot be empty."
            return
        }
        val params: MutableMap<String, String> = mapOf(
            "userNameMobileNo" to userNameMobileNo,
            "passWord" to password,
            "loginPlatform" to "ANDROID",
            "loginIpAddress" to ""
        ).toMutableMap()
        viewModelScope.launch (Dispatchers.IO) {
            loginRepository.userLogin(params) { jsonObject: JSONObject ->
                try {
                    val apiResponse: ApiPadhaiResponse? = handleApiResponse(jsonObject)
                    if(apiResponse != null) {
                        val loginModel = Gson().fromJson(apiResponse.Response, LoginModel::class.java)
                        Log.d("LoginViewModel", loginModel.toString())
                        _loginMutableFlow.value = loginModel
                    }
                } catch (ex: Exception) {
                    _errorMutableFlow.value = ex.message
                }
            }
        }
    }
}