package com.udit.todoit.room.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.udit.todoit.room.entity.TodoType

@Dao
interface TodoTypeDao {
    @Upsert
    suspend fun upsertTodoType(todoType: TodoType)
}