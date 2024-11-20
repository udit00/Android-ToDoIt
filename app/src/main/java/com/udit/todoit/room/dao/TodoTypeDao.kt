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


    @Query("Select * from todotype where typeId = :todoTypeId")
    fun getTodoTypeDetails(todoTypeId: Int): Flow<TodoType?>

    @Query("select iif((select count(*) from todotype where typename = :typeName) > 0, 1, 0)")
    suspend fun checkIfTodoTypeAlreadyExists(typeName: String): Boolean
}