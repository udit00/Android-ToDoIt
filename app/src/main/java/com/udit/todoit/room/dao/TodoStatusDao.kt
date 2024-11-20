package com.udit.todoit.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.udit.todoit.room.entity.TodoStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoStatusDao {

    @Upsert
    suspend fun upsertTodoStatus(todoStatus: List<TodoStatus>)

    @Upsert
    suspend fun upsertTodoStatus(todoStatus: TodoStatus)

    @Query("Select count(*) from todostatus")
    fun getCountOfStatus(): Flow<Int>
}