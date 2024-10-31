package com.udit.todoit.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.udit.todoit.room.entity.TodoType
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoTypeDao {
    @Upsert
    suspend fun upsertTodoType(todoType: TodoType)

    @Query("Select * from todotype")
    fun getTodoTypes(): Flow<List<TodoType>>
}