package com.example.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

@Entity (tableName = "Items")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "creationDate") val creationDate: LocalDateTime? = null,
    @ColumnInfo(name = "Date") val date: LocalDateTime? = null,
    @ColumnInfo(name = "isCompleted") val isCompleted: Boolean = false
) {

    @Override
    override fun toString(): String {
        return isCompleted.toString().plus(" $name")
    }
}