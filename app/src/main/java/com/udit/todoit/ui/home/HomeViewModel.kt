package com.udit.todoit.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository): ViewModel() {
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