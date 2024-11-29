package com.udit.todoit.ui.add_todo_type.models

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class TodoTypeView(
    val todoTypeId: Int,
    val todoTypeName: String,
    val color: String,
    val isLight: Boolean,
    val totalCount: Int,
    val pendingCount: Int,
    val completedCount: Int,
    val laterCount: Int
)
