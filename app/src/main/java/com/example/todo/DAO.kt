package com.example.todo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {
    @Insert
    fun insertItem(item: Todo)
    @Query("SELECT * FROM items")
    fun getAllItems() : Flow<List<Todo>>
}