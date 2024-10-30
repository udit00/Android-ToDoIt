package com.udit.todoit.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.RoomSQLiteQuery
import java.sql.Timestamp
import java.time.LocalDateTime

//@Entity(tableName = "todo")
//data class Todo(
//    val createdon: String,
//    val createid: Int,
//    val description: String,
//    val target: String,
//    val title: String,
//    @PrimaryKey(autoGenerate = true)
//    val todoid: Int,
//    val todotypeid: Int,
//    val typeid: Int,
//    val typename: String,
//    val userid: Int
//)

@Entity(tableName = "todo",
    foreignKeys = [
        ForeignKey(entity = TodoType::class, onDelete = ForeignKey.CASCADE, parentColumns = ["typeId"], childColumns = ["todoTypeID"])
    ]
)
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val todoID: Int = 0,
    val createId: Int,
    val title: String,
    val description: String,
    val createdOn: String,
    val target: String,
    val todoTypeID: Int,
)