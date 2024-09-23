package com.udit.todoit.api.volley

import com.android.volley.Request.Method
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.udit.todoit.api.data.ApiPadhai
import com.udit.todoit.entry_point.application.MyApp
import org.json.JSONObject
import javax.inject.Inject


open class VolleyImpl @Inject constructor(): ApiPadhai {
    override fun get(
        apiName: String,
        params: Map<String, String>,
        success: (jsonObject: JSONObject) -> Unit,
        error: (errMsg: String) -> Unit
    ) {
        val url = ApiPadhai.BASE_URL + apiName

        val jsonObjectRequest = JsonObjectRequest(Method.GET, url, null,
            { response ->
                success(response)
            },
            { err ->
                err.message?.let { it -> error(it) }
            }
        )
    }

    override fun post(
        apiName: String,
        params: Map<String, String>,
        success: (jsonObject: JSONObject) -> Unit,
        error: (errMsg: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun update(
        apiName: String,
        params: Map<String, String>,
        success: (jsonObject: JSONObject) -> Unit,
        error: (errMsg: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun delete(
        apiName: String,
        params: Map<String, String>,
        success: (jsonObject: JSONObject) -> Unit,
        error: (errMsg: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }


}