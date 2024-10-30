package com.udit.todoit.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udit.todoit.room.dao.TodoDao
import com.udit.todoit.room.dao.TodoTypeDao
import com.udit.todoit.room.entity.Todo
import com.udit.todoit.room.entity.TodoType


@Database(
    entities = [Todo::class, TodoType::class],
    version = 1,
    exportSchema = false,
)
abstract class TodoDatabase: RoomDatabase() {
    abstract val todoDao: TodoDao
    abstract val todoTypeDao: TodoTypeDao
}