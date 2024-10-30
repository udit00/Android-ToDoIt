package com.udit.todoit.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.udit.todoit.room.entity.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Upsert
    suspend fun upsertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo")
    fun getAllTodo(): Flow<List<Todo>>

//    @Query("Select tt.typename, t.* from todo t inner join todotype tt on tt.typeid where todoid = ?")
//    fun getTodo(todoId: Todo): Flow<Todo>

}