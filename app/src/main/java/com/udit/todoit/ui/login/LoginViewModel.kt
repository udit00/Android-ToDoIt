package com.udit.todoit.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.udit.todoit.api.data.apipadhai.ApiPadhaiResponse
import com.udit.todoit.base.BaseViewModel
import com.udit.todoit.entry_point.main_activity.navigation.Screen
import com.udit.todoit.navigation.nav_provider.NavigationProvider
import com.udit.todoit.shared_preferences.StorageHelper
import com.udit.todoit.ui.login.model.LoginModel
import com.udit.todoit.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val storageHelper: StorageHelper,
    private val navigationProvider: NavigationProvider
): BaseViewModel() {


//    private val _loginMutableFlow: MutableStateFlow<LoginModel?> = MutableStateFlow(null)
//    val loginSuccessful get() = _loginMutableFlow.asStateFlow().drop(1)

    private val _isLoadingMutableFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val isLoading get() = _isLoadingMutableFlow.asSharedFlow()

    val userNameMobileNo = mutableStateOf("7011490531")
    val passWord = mutableStateOf("123456")
    val passwordVisibility = mutableStateOf(false)

    init {
        viewModelScope.launch {
            loginRepository.errorFlow.collectLatest { errMsg ->
                notifyUserAboutError(errMsg)
                hideLoading()
            }
        }
        if(ifUserAlreadyLoggedIn()) {
            navigateToHomePage()
        }
    }

    fun localLogger(str: String?): Unit {
        str?.let { s ->
            Log.d(this@LoginViewModel.javaClass.simpleName, s)
        }
    }

    private fun showLoading() {
        viewModelScope.launch {
            _isLoadingMutableFlow.emit(true)
        }
    }

    private fun hideLoading() {
        viewModelScope.launch {
            _isLoadingMutableFlow.emit(false)
        }
    }

    private fun ifUserAlreadyLoggedIn(): Boolean {
        val loginData = storageHelper.getLoginData()
        if(loginData != null) {
//            Log.d("", loginData.toString())
            if(loginData.UserID > 0
                && loginData.MobileNo.isNotBlank()
                && loginData.Password.isNotBlank()) {
                return true
            } else if(loginData.isGuest) {
                return true
            }
        }
        return false
    }

    fun loginUser() {
        showLoading()
        val user: String = this.userNameMobileNo.value
        val pass = this.passWord.value
        if(user.isBlank()) {
            notifyUserAboutError("UserName cannot be empty.")
            hideLoading()
            return
        } else if(pass.isBlank()) {
            notifyUserAboutError("Password cannot be empty.")
            hideLoading()
            return
        }
        val params: MutableMap<String, String> = mapOf(
            "userNameMobileNo" to user,
            "passWord" to pass,
            "loginPlatform" to "ANDROID",
            "loginIpAddress" to Utils.getIpAddress()
        ).toMutableMap()
        viewModelScope.launch (Dispatchers.IO) {
            loginRepository.userLogin(params) { jsonObject: JSONObject ->
                try {
                    hideLoading()
                    val apiResponse: ApiPadhaiResponse? = handleApiResponse(jsonObject)
                    if(apiResponse != null) {
                        val loginModel = Gson().fromJson(apiResponse.Response, LoginModel::class.java)
                        setLoginToStorage(loginModel)
                        navigateToHomePage()
                    }
                } catch (ex: Exception) {
                    hideLoading()
                    if(ex.message != null) {
                        notifyUserAboutError(ex.message!!)
                    } else {
                        notifyUserAboutError("Something went wrong.")
                    }
                }
            }
        }
    }

    fun guestLoginUser() {
//        showLoading()
        val loginModel = LoginModel(
            UserID = 0,
            Name = "Guest Login",
            MobileNo = "GUEST_LOGIN",
            Password = "GUEST_LOGIN",
            EmailID = "GUEST_LOGIN",
            IsPremium = false,
            IsActive = true,
            DisplayPicture = "",
            CreatedOn = "",
            FirebaseToken = "",
            isGuest = true
        )
        setLoginToStorage(loginModel)
        navigateToHomePage()
    }

    private fun setLoginToStorage(loginModel: LoginModel) {
        val gson = Gson()
        val loginSerializedStr = gson.toJson(loginModel)
        storageHelper.setString(StorageHelper.LOGIN_PREF_TAG, loginSerializedStr.toString())
    }

    private fun navigateToHomePage() {
        navigationProvider.navController.navigate(Screen.HomePage) {
            popUpTo(0)
        }
    }
}