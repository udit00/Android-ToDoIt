package com.udit.todoit.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.udit.todoit.api.data.apipadhai.ApiPadhaiResponse
import com.udit.todoit.ui.login.model.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(): ViewModel() {

//    protected val _errorMutableFlow = MutableSharedFlow<String?>()
//    val errorFlow get() = _errorMutableFlow.asSharedFlow()

    protected val _errorMutableFlow = MutableSharedFlow<String>()
    val errorFlow get() = _errorMutableFlow

    fun handleApiResponse(jsonObject: JSONObject): ApiPadhaiResponse? {
        var apiPadhaiResponse: ApiPadhaiResponse? = null
        try {
            val gson = Gson()
            apiPadhaiResponse = gson.fromJson(jsonObject.toString(), ApiPadhaiResponse::class.java)
        } catch (ex: Exception) {
            viewModelScope.launch {
//                _errorMutableFlow.emit(ex.message)
                _errorMutableFlow.emit(ex.message.toString());
            }
        }
        return apiPadhaiResponse
    }

    fun notifyUserAboutError(errMsg: String?) {
        viewModelScope.launch {
            if (errMsg != null) {
                _errorMutableFlow.emit(errMsg)
            } else {
                _errorMutableFlow.emit("Something went wrong.")
            }
        }
    }

    fun logger(msg: String) {
        Log.d("ViewModel", msg)
    }
}