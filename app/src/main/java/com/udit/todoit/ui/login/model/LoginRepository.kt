package com.udit.todoit.ui.login.model

import com.android.volley.toolbox.Volley
import com.udit.todoit.api.Api
import javax.inject.Inject


class LoginRepository @Inject constructor(private val api: Api) {

//    fun userLogin(params: Map<String, String>) {
//        api.get(params)
//    }
//    fun userLogin() {
//        val queue = Volley.newRequestQueue(this)
//        val url = "https://www.google.com"
//
//// Request a string response from the provided URL.
//        val stringRequest = StringRequest(Request.Method.GET, url,
//            Response.Listener<String> { response ->
//                // Display the first 500 characters of the response string.
//                textView.text = "Response is: ${response.substring(0, 500)}"
//            },
//            Response.ErrorListener { textView.text = "That didn't work!" })
//
//// Add the request to the RequestQueue.
//        queue.add(stringRequest)
//    }
}