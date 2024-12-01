package com.udit.todoit.ui.upsert_todo

import com.udit.todoit.room.TodoDatabase
import com.udit.todoit.room.entity.Todo
import com.udit.todoit.room.entity.TodoType
import com.udit.todoit.ui.home.model.TodoView
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import javax.inject.Inject

class UpsertTodoRepository @Inject constructor(
    private val roomDB: TodoDatabase
) {

    private val _error: MutableSharedFlow<String?> = MutableSharedFlow()
    val errorFlow get() = _error.asSharedFlow()

    suspend fun getTodoDataByID(todoId: Int, response: (todoData: Todo) -> Unit) {
        try {
            val todoData = roomDB.todoDao.getTodo(todoId = todoId)
            response(todoData)
        } catch (ex: Exception) {
            _error.tryEmit(ex.message)
        }

    }

    suspend fun getTodoTypeDataById(todoTypeId: Int, response: (todoTypeData: TodoType) -> Unit) {
        try {
            roomDB.todoTypeDao.getTodoTypeDetails(todoTypeId = todoTypeId).collectLatest {
                if (it != null) {
                    response(it)
                }
            }
        } catch (ex: Exception) {
            _error.tryEmit(ex.message)
        }
    }

    suspend fun getTypesForList(listTodoTypes: (List<TodoType>) -> Unit) {
        roomDB.todoTypeDao.getTodoTypes().collectLatest { types ->
            listTodoTypes(types)
        }
    }

    suspend fun ifTodoAlreadyExists(todo: Todo, ifExists: (Boolean) -> Unit) {
        val doesTheTodoExists = roomDB.todoDao.ifTodoAlreadyExists(title = todo.title, description = todo.description)
        ifExists(doesTheTodoExists)
    }

    suspend fun upsertTodo(todo: Todo) {
        roomDB.todoDao.upsertTodo(todo)
    }

}