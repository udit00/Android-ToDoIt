package com.udit.todoit.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udit.todoit.room.dao.TodoDao
import com.udit.todoit.room.entity.Todo


@Database(
    entities = [Todo::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase: RoomDatabase() {
    abstract val todoDao: TodoDao
}