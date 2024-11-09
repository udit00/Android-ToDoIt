package com.udit.todoit.ui.add_todo_type

import com.udit.todoit.room.TodoDatabase
import com.udit.todoit.room.entity.TodoType
import javax.inject.Inject


class AddTodoTypeRepository @Inject constructor(private val roomDB: TodoDatabase) {

    suspend fun upsertTodoType(todoType: TodoType) {
        roomDB.todoTypeDao.upsertTodoType(todoType)
    }

}