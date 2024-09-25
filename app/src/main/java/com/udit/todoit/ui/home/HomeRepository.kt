package com.udit.todoit.ui.home

import com.udit.todoit.api.Api
import javax.inject.Inject


class HomeRepository @Inject constructor(private val api: Api) {
    suspend fun getTodos(params: Map<String, String>) {
        api.get("getTodos", params, {

        }, {

        })
    }
}