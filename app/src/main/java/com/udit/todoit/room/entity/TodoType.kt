package com.udit.todoit.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "todotype")
data class TodoType(
    @SerializedName("type_name")
    val typename: String,
    @SerializedName("type_id")
    @PrimaryKey(autoGenerate = true)
    val typeId: Int = 0,
    val color: String
)
