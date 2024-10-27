package com.udit.todoit.ui.home

import com.google.gson.Gson
import com.udit.todoit.api.Api
import com.udit.todoit.api.data.apipadhai.ApiPadhaiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import org.json.JSONObject
import javax.inject.Inject


class HomeRepository @Inject constructor(private val api: Api) {

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorFlow get() = _error.asStateFlow().drop(1)

//    private val _todos: MutableStateFlow<JSONObject?> = MutableStateFlow(null)
//    val todos get() = _todos.asStateFlow().drop(1)

    suspend fun getTodos(params: Map<String, String>, response: (jsonObject: JSONObject) -> Unit) {
        api.get("getTodos", params, { jsonObject ->
            try {
//                _todos.value = jsonObject
                response(jsonObject)
            } catch (ex: Exception) {
                _error.value = ex.message
            }
        }, { errorMessage ->
            _error.value = errorMessage
        })
    }

    suspend fun getTodosFromRoomDb() {

    }
}