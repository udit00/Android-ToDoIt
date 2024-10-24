package com.udit.todoit.ui.login

import com.udit.todoit.api.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import org.json.JSONObject
import javax.inject.Inject


class LoginRepository @Inject constructor(private val api: Api) {

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorFlow get() = _error.asStateFlow().drop(1)

    suspend fun userLogin(params: Map<String, String>, response: (jsonObject: JSONObject) -> Unit) {
        api.post("userLogin", params, { jsonObject: JSONObject ->
            try {
                response(jsonObject)
            } catch (ex: Exception) {
                _error.value = ex.message
            }
        }, { errMsg: String ->
            _error.value = errMsg
        })
    }

}