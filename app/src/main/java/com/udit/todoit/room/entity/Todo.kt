package com.udit.todoit.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
    val createdon: String,
    val createid: Int,
    val description: String,
    val target: String,
    val title: String,
    @PrimaryKey(autoGenerate = true)
    val todoid: Int,
    val todotypeid: Int,
    val typeid: Int,
    val typename: String,
    val userid: Int
)