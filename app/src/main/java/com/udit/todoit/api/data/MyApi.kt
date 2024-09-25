package com.udit.todoit.api.data

import com.android.volley.Cache
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import org.json.JSONObject
import java.io.File

interface ApiPadhai {
    suspend fun get(apiName: String, params: Map<String, String>,  success: (jsonObject: JSONObject) -> Unit, error: (errMsg: String) -> Unit)
    suspend fun post(apiName: String, params: Map<String, String>,  success: (jsonObject: JSONObject) -> Unit, error: (errMsg: String) -> Unit)
    suspend fun update(apiName: String, params: Map<String, String>, success: (jsonObject: JSONObject) -> Unit, error: (errMsg: String) -> Unit)
    suspend fun delete(apiName: String, params: Map<String, String>,  success: (jsonObject: JSONObject) -> Unit, error: (errMsg: String) -> Unit)

    companion object {
//        const val BASE_URL: String = "https://www.apipadhai.com/todo/"
        const val BASE_URL: String = "http://157.173.218.215:5000/todo/"

    }
}