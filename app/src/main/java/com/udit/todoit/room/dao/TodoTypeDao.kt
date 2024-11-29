package com.udit.todoit.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.udit.todoit.room.entity.TodoType
import com.udit.todoit.ui.add_todo_type.models.TodoTypeCount
import com.udit.todoit.ui.add_todo_type.models.TodoTypeView
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoTypeDao {
    @Upsert
    suspend fun upsertTodoType(todoType: TodoType)

    @Query("Select * from todotype")
    fun getTodoTypes(): Flow<List<TodoType>>

    @Query("Select type.typeId as todoTypeId, type.typename as todoTypeName, type.color as color, type.isLight as isLight, " +
            "(Select count(t.todoID)" +
            "from todo t " +
            "inner join todotype tt on tt.typeId = t.todoTypeID " +
            "where typeId = type.typeId ) as totalCount, " +
            "(Select count(t.todoID)" +
            "from todo t " +
            "inner join todotype tt on tt.typeId = t.todoTypeID " +
            "where typeId = type.typeId "+
            "and todoCompletionStatusID = 1) as pendingCount, " +
            "(Select count(t.todoID)" +
            "from todo t " +
            "inner join todotype tt on tt.typeId = t.todoTypeID " +
            "where typeId = type.typeId " +
            "and todoCompletionStatusID = 2) as completedCount, " +
            "(Select count(t.todoID)" +
            "from todo t " +
            "inner join todotype tt on tt.typeId = t.todoTypeID " +
            "where typeId = type.typeId " +
            "and todoCompletionStatusID = 3) as laterCount " +
            "from todotype type")
    fun getTodoTypesView(): Flow<List<TodoTypeView>>

    @Query("Select * from todotype where typeId = :todoTypeId")
    fun getTodoTypeDetails(todoTypeId: Int): Flow<TodoType?>

    @Query("Select typeId as todoTypeId, typename as todoTypeName, " +
            "(Select count(t.todoID)" +
            "from todo t " +
            "inner join todotype tt on tt.typeId = t.todoTypeID " +
            "where typeId = :todoTypeId ) as totalCount, " +
            "(Select count(t.todoID)" +
            "from todo t " +
            "inner join todotype tt on tt.typeId = t.todoTypeID " +
            "where typeId = :todoTypeId " +
            "and todoCompletionStatusID = 1) as pendingCount, " +
            "(Select count(t.todoID)" +
            "from todo t " +
            "inner join todotype tt on tt.typeId = t.todoTypeID " +
            "where typeId = :todoTypeId " +
            "and todoCompletionStatusID = 2) as completedCount, " +
            "(Select count(t.todoID)" +
            "from todo t " +
            "inner join todotype tt on tt.typeId = t.todoTypeID " +
            "where typeId = :todoTypeId " +
            "and todoCompletionStatusID = 3) as laterCount " +
            "from todotype " +
            "where todotype.typeId = :todoTypeId")
    suspend fun getTodoTypeCount(todoTypeId: Int): TodoTypeCount

    @Query("select iif((select count(*) from todotype where typename = :typeName) > 0, 1, 0)")
    suspend fun checkIfTodoTypeAlreadyExists(typeName: String): Boolean
}