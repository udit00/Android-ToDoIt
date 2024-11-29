package com.udit.todoit.ui.add_todo_type.models

data class TodoTypeCount(
    val todoTypeId: Int,
    val todoTypeName: String,
    val totalCount: Int,
    val pendingCount: Int,
    val completedCount: Int,
    val laterCount: Int
)
