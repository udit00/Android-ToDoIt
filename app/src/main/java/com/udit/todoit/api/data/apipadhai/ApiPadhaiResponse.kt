package com.udit.todoit.api.data.apipadhai

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.udit.todoit.ui.home.data.Todo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//sealed class JsonType {
//    data class
//}

data class ApiPadhaiResponse (
    var Message: String,
    val Status: Int,
    val Response: JsonElement,
//    @SerialName("Success") val success: Boolean
//    val Success: Boolean = (Status == 1)
)