package com.udit.todoit.api.data

import com.android.volley.Cache
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import org.json.JSONObject
import java.io.File

interface ApiPadhai {
    fun get(apiName: String, params: Map<String, String>,  success: (jsonObject: JSONObject) -> Unit, error: (errMsg: String) -> Unit)
    fun post(apiName: String, params: Map<String, String>,  success: (jsonObject: JSONObject) -> Unit, error: (errMsg: String) -> Unit)
    fun update(apiName: String, params: Map<String, String>, success: (jsonObject: JSONObject) -> Unit, error: (errMsg: String) -> Unit)
    fun delete(apiName: String, params: Map<String, String>,  success: (jsonObject: JSONObject) -> Unit, error: (errMsg: String) -> Unit)

    companion object {
        const val BASE_URL: String = "https://www.apipadhai.com/todo/"

    }
}