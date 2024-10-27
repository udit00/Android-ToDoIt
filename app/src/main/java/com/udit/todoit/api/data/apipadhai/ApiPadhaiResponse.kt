package com.udit.todoit.api.data.apipadhai

import com.google.gson.JsonElement

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