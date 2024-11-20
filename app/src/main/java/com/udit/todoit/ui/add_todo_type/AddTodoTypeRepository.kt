package com.udit.todoit.ui.add_todo_type

import com.udit.todoit.room.TodoDatabase
import com.udit.todoit.room.entity.TodoType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.toCollection
import javax.inject.Inject


class AddTodoTypeRepository @Inject constructor(private val roomDB: TodoDatabase) {


    suspend fun upsertTodoType(todoType: TodoType) {
        roomDB.todoTypeDao.upsertTodoType(todoType)
    }

    suspend fun ifTodoTypeExists(typeName: String, exists: (doesExists: Boolean) -> Unit)  {
        return exists(roomDB.todoTypeDao.checkIfTodoTypeAlreadyExists(typeName))
    }

    fun getTodoTypeDetails(todoTypeId: Int): Flow<TodoType?> {
        return roomDB.todoTypeDao.getTodoTypeDetails(todoTypeId = todoTypeId)
    }

}