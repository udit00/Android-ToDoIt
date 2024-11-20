package com.udit.todoit.ui.home.model

import com.udit.todoit.room.entity.Todo

data class TodoView(
    val todoID: Int = 0,
    val createId: Int,
    val title: String,
    val description: String,
    val createdOn: String,
    val target: String,
    val todoTypeID: Int,
    val todoCompletionStatusID: Int,
    val todoTypeName: String
) {
//    companion object {
//        fun getTodoView(todo: Todo): TodoView {
//            val todoView = TodoView(
//                todoID = todo.todoID,
//                todoTypeID = todo.todoTypeID
//            )
//            this.todoID = todo.todoID
//        }
//    }
}
