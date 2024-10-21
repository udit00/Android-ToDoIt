package com.udit.todoit.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.udit.todoit.api.data.apipadhai.ApiPadhaiResponse
import com.udit.todoit.ui.login.model.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(): ViewModel() {

    protected val _errorMutableFlow = MutableStateFlow<String?>(null)
    val errorFlow get() = _errorMutableFlow.asStateFlow()

    fun handleApiResponse(jsonObject: JSONObject): ApiPadhaiResponse? {
        var apiPadhaiResponse: ApiPadhaiResponse? = null
        try {
            val gson = Gson()
            apiPadhaiResponse = gson.fromJson(jsonObject.toString(), ApiPadhaiResponse::class.java)
        } catch (ex: Exception) {
            _errorMutableFlow.value = ex.message
        }
        return apiPadhaiResponse
    }

    fun logger(msg: String) {
        Log.d("ViewModel", msg)
    }
}