package com.udit.todoit.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    fun loginUser(mobile: String, password: String) {
        val params: MutableMap<String, String> = mapOf(
            "userNameMobileNo" to mobile,
            "passWord" to password,
            "loginPlatform" to "ANDROID",
            "loginIpAddress" to ""
        ).toMutableMap()
        viewModelScope.launch (Dispatchers.IO) {
            loginRepository.userLogin(params) { jsonObject: JSONObject ->
                try {
                    val apiResponse: ApiPadhaiResponse<LoginModel>? = handleApiResponse<LoginModel>(jsonObject, LoginModel::class.java)

                    if(apiResponse != null) {
//                        val loginModel = Gson().fromJson(apiResponse.Response, LoginModel::class.java)
                        val loginModel = apiResponse.Response
                        Log.d("LoginViewModel", loginModel.toString())

//                        val gson = Gson()
//                        val typeToken = object: TypeToken<Array<LoginModel>>(){}.type
//                        val loginModelList = gson.fromJson<Array<LoginModel>>(apiResponse.data[0].toString(), typeToken)
//                        if (loginModelList.isNotEmpty()) {
//                            _loginMutableFlow.value = loginModelList[0]
//                        }
                    }
                } catch (ex: Exception) {
                    _errorMutableFlow.value = ex.message
                }
            }
        }
    }
}