package com.udit.todoit.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.udit.todoit.api.data.apipadhai.ApiPadhaiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(): ViewModel() {

    protected val _errorMutableFlow = MutableStateFlow<String?>(null)
    val errorFlow get() = _errorMutableFlow.asStateFlow()

    fun handleApiResponse(jsonObject: JSONObject?): ApiPadhaiResponse? {
        if(jsonObject != null) {
            try {
                val apiRawRes = Gson().fromJson(jsonObject.toString(), ApiPadhaiResponse::class.java)
                if(apiRawRes.success) {
                    return apiRawRes
                }
            } catch (ex: Exception) {
                _errorMutableFlow.value = ex.message
            }
        } else {
            _errorMutableFlow.value = "Could not parse response."
        }
        return null
    }

    fun logger(msg: String) {
        Log.d("ViewModel", msg)
    }
}