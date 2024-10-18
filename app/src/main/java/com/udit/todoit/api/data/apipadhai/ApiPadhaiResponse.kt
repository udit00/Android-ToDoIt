package com.udit.todoit.api.data.apipadhai

import com.google.gson.JsonArray
import com.udit.todoit.ui.home.data.Todo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class ApiPadhaiResponse<T> (
    var Message: String,
    val Status: Int,
    val Response: T,
//    @SerialName("Success") val success: Boolean
//    val Success: Boolean = (Status == 1)
)