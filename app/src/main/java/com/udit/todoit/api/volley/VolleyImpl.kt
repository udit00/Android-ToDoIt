package com.udit.todoit.api.volley

import android.util.Log
import com.android.volley.Cache
import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.udit.todoit.api.data.apipadhai.ApiPadhai
import com.udit.todoit.api.data.apipadhai.ApiPadhaiResponse
import com.udit.todoit.entry_point.application.MyApp
import org.json.JSONObject
import javax.inject.Inject


open class VolleyImpl @Inject constructor(private val myApp: MyApp): ApiPadhai {

    private val requestQueue: RequestQueue by lazy {
        val cache: Cache = DiskBasedCache(myApp.cacheDir, 1024 * 1024)
        val network = BasicNetwork(HurlStack())
        RequestQueue(cache, network).apply { start() }
    }

    fun logApiResponse(apiName: String, jsonObject: JSONObject) {
        Log.d("VOLLEY_API_RESPONSE", "----------- START --------------")
        Log.d("VOLLEY_API_RESPONSE", apiName)
        Log.d("VOLLEY_API", jsonObject.toString(1))
    }

    private fun getUrlWithParams(baseUrlWithApiName: String, params: Map<String, String>): String {
        val finalUrl: StringBuilder = StringBuilder(baseUrlWithApiName + if(params.isEmpty()) "" else "?")
        for(param in params) finalUrl.append(param.key + "=" + param.value + "&")
        if(finalUrl[finalUrl.length - 1] == '?') {
            finalUrl.deleteCharAt(finalUrl.length - 1)
        }
        return finalUrl.toString()
    }

    override suspend fun get(
        apiName: String,
        params: Map<String, String>,
        success: (jsonObject: JSONObject) -> Unit,
        error: (errMsg: String) -> Unit
    ) {
        val url = getUrlWithParams(ApiPadhai.BASE_URL + apiName, params)
        val jsonObjectRequest = JsonObjectRequest(Method.GET, url, null,
            { response ->
                logApiResponse(apiName, response)
                if(response != null) {
                    success(response)
                } else {
                    error("Something went wrong, Please try again.")
                }
            },
            { err ->
                err.message?.let { it -> error(it) }
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

    override suspend fun post(
        apiName: String,
        params: Map<String, String>,
        success: (jsonObject: JSONObject) -> Unit,
        error: (errMsg: String) -> Unit
    ) {
        val url = ApiPadhai.BASE_URL + apiName
        val jsonObject = JSONObject(params)
        val jsonObjectRequest = JsonObjectRequest(Method.POST, url, jsonObject,
            { response ->
                logApiResponse(apiName, response)
                if(response != null) {
                    success(response)
                } else {
                    error("Something went wrong, Please try again.")
                }
            },
            { err ->
                err.message?.let { it -> error(it) }
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

    override suspend fun update(
        apiName: String,
        params: Map<String, String>,
        success: (jsonObject: JSONObject) -> Unit,
        error: (errMsg: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(
        apiName: String,
        params: Map<String, String>,
        success: (jsonObject: JSONObject) -> Unit,
        error: (errMsg: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }


}