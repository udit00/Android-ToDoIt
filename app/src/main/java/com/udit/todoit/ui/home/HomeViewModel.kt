package com.udit.todoit.ui.home

import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonArray
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
            "prmuserid" to "1",
            "prmcharstr" to searchedString
        )
        viewModelScope.launch {
            homeRepository.getTodos(params) { jsonObject: JSONObject ->
                try {
                    val apiResponse: ApiPadhaiResponse<Todo>? = handleApiResponse(jsonObject, Todo::class.java)

                    if(apiResponse != null) {
                        val gson = Gson()
                        val typeToken = object: TypeToken<Array<Todo>>(){}.type
//                        val todosList = gson.fromJson<Array<Todo>>(apiResponse.data[0].toString(), typeToken)
//                        if (todosList.isNotEmpty()) {
//                            _todos.value = todosList
//                        }
                    }
                } catch (ex: Exception) {
                    _errorMutableFlow.value = ex.message
                }
            }
        }
    }
}