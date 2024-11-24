package com.udit.todoit.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "todostatus"
)
data class TodoStatus(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("status_id")
    val statusID: Int = 0,
    val statusName: String,
    val statusColor: String,
    val isColorLight: Boolean
)
{

//    override fun toString(): String {
//       return statusName
//    }
}