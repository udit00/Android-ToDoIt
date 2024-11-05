package com.udit.todoit.ui.upsert_todo

import com.udit.todoit.room.TodoDatabase
import com.udit.todoit.room.entity.TodoType
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class UpsertTodoRepository @Inject constructor(
    private val roomDB: TodoDatabase
) {

    suspend fun getTypesForList(listTodoTypes: (List<TodoType>) -> Unit) {
        roomDB.todoTypeDao.getTodoTypes().collectLatest { types ->
            listTodoTypes(types)
        }
    }

}