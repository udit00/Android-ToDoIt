package com.udit.todoit.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.udit.todoit.room.entity.Todo
import com.udit.todoit.room.entity.TodoType
import com.udit.todoit.ui.home.model.TodoView
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Upsert
    suspend fun upsertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo")
    fun getAllTodo(): Flow<List<Todo>>

    @Query("SELECT t.*, " +
            "tt.typename as todoTypeName, tt.color as todoTypeColor, tt.isLight as todoTypeIsLight, " +
            "ts.statusName as todoStatusName, ts.statusColor as todoStatusColor, ts.isColorLight as todoStatusIsLight " +
            "FROM todo t " +
            "inner join todotype tt on tt.typeId = t.todoTypeID " +
            "inner join todostatus ts on ts.statusID = t.todoCompletionStatusID ")
    fun getAllTodoAsView(): Flow<List<TodoView>>

    @Query("SELECT EXISTS(select * from todo where title = :title and description = :description)")
    suspend fun ifTodoAlreadyExists(title: String, description: String): Boolean

//    @Query("Select tt.typename, t.* from todo t inner join todotype tt on tt.typeid where todoid = :todoId")
//    fun getTodo(todoId: Todo): Flow<Todo>
//
//    @Query("Select tt.typename, t.* from todo t inner join todotype tt on tt.typeid where t.todoTypeID = :todoTypeId")
//    fun getAllTodosFilterByType(todoTypeId: Int): Flow<List<Todo>>


}