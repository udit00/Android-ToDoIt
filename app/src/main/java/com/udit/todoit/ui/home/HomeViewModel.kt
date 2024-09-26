package com.udit.todoit.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.udit.todoit.api.data.apipadhai.ApiPadhaiResponse
import com.udit.todoit.base.BaseViewModel
import com.udit.todoit.ui.home.data.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository): BaseViewModel() {



    init {
        viewModelScope.launch {
            setObservers()
        }
    }

    private suspend fun setObservers() {
        homeRepository.todos.collectLatest { jsonObject: JSONObject? ->
            try {
                val apiResponse: ApiPadhaiResponse? = handleApiResponse(jsonObject)
                if(apiResponse != null) {
                    val todosList: ArrayList<Todo> = apiResponse.data[0] as ArrayList<Todo>
                    val randomTodo = todosList[0] as Todo
                    logger(randomTodo.target)
                }
            } catch (ex: Exception) {
                _errorMutableFlow.value = ex.message
            }
        }
    }

    fun getTodos(searchValue: String? = "") {
        val searchedString: String = if(searchValue.isNullOrBlank()) "" else searchValue
        val params = mapOf(
            "prmuserid" to "1",
            "prmcharstr" to searchedString
        )
        viewModelScope.launch {
            homeRepository.getTodos(params)
        }
    }
}