package com.udit.todoit.api.data.apipadhai

import org.json.JSONObject

interface ApiPadhai {
    suspend fun get(apiName: String, params: Map<String, String>,  success: (jsonObject: JSONObject) -> Unit, error: (errMsg: String) -> Unit)
    suspend fun post(apiName: String, params: Map<String, String>,  success: (jsonObject: JSONObject) -> Unit, error: (errMsg: String) -> Unit)
    suspend fun update(apiName: String, params: Map<String, String>, success: (jsonObject: JSONObject) -> Unit, error: (errMsg: String) -> Unit)
    suspend fun delete(apiName: String, params: Map<String, String>,  success: (jsonObject: JSONObject) -> Unit, error: (errMsg: String) -> Unit)

    companion object {
//        const val BASE_URL: String = "https://www.apipadhai.com/todo/"
//        const val BASE_URL: String = "http://157.173.218.215:5000/todo/"
        const val BASE_URL: String = "http://srv554627.hstgr.cloud:10000/API/todo/"

    }
}