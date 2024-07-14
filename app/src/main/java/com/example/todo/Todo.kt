package com.example.todo

import java.util.Date
import java.util.UUID

class Todo(
    val id: UUID? = UUID.randomUUID(),
    val name: String? = null,
    val description: String? = null,
    val date: Date = Date(),
    val isCompleted: Boolean = false
) {

    @Override
    override fun toString(): String {
        return isCompleted.toString().plus(" $name")
    }
}