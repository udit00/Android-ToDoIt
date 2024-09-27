package com.udit.todoit.api.data.apipadhai

import com.google.gson.JsonArray
import com.udit.todoit.ui.home.data.Todo
import kotlinx.serialization.SerialName

data class ApiPadhaiResponse (
    @SerialName("message") var message: String,
    @SerialName("status") val status: Int,
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: List<JsonArray>
)