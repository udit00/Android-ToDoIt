package com.udit.todoit.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.udit.todoit.api.data.apipadhai.ApiPadhaiResponse
import com.udit.todoit.base.BaseViewModel
import com.udit.todoit.ui.home.data.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository): BaseViewModel() {

    private val _todos: MutableStateFlow<Array<Todo>> = MutableStateFlow(arrayOf())
    val todos get() = _todos.asStateFlow()

//    init {
//        viewModelScope.launch {
//            setObservers()
//        }
//    }

    fun getTodos(searchValue: String? = "") {
        val searchedString: String = if(searchValue.isNullOrBlank()) "" else searchValue
        val params = mapOf(
            "userId" to "1",
            "charStr" to searchedString
        )
        viewModelScope.launch {
            homeRepository.getTodos(params) { jsonObject: JSONObject ->
                try {
//                    val typeToken = object: TypeToken<JsonObject>() {}
//                    val apiResponse = handleApiResponse(jsonObject, typeToken)
                    val apiResponse = handleApiResponse(jsonObject)
//
                    if(apiResponse != null) {
                        val typeToken = object: TypeToken<ArrayList<Todo>>(){}.type
                        val todoList = Gson().fromJson<ArrayList<Todo>>(apiResponse.Response, typeToken)
                        Log.d("HOME VIEW MODEL", todoList.toString())
                    }
                } catch (ex: Exception) {
                    _errorMutableFlow.value = ex.message
                }
            }
        }
    }
}