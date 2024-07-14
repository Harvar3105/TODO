package com.example.todo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Date
import java.util.UUID

class DAO(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "todo-app", factory, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE todo (id TEXT PRIMARY KEY, name TEXT, description TEXT, date TEXT, isCompleted BOOLEAN)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS todo")
        onCreate(db)
    }

    fun add(todo: Todo){
        val values = ContentValues()
        values.put("id", todo.id.toString())
        values.put("name", todo.name)
        values.put("description", todo.description)
        values.put("date", todo.date.toString())
        values.put("isCompleted", todo.isCompleted)

        val db = this.writableDatabase
        db.insert("todo", null, values)

        db.close()
    }

    fun getAll() : List<Todo> {
        val db = this.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM todo", null)
        var result: MutableList<Todo> = mutableListOf()

        do {
            val id = UUID.fromString(cursor.getString(cursor.getColumnIndexOrThrow("id")))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
            val date = Date(cursor.getString(cursor.getColumnIndexOrThrow("date")))
            val isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow("isCompleted")) == 1

            val todo = Todo(id, name, description, date, isCompleted)
            result.add(todo)
        } while (cursor.moveToNext())

        cursor.close()
        return result
    }
}