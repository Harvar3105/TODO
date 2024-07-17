package com.example.todo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database (entities = [Todo::class], version = 1)
@TypeConverters(DbConverter::class)
abstract class DataBase : RoomDatabase() {
    abstract fun getDAO(): DAO

    companion object {
        fun getDB(ctx: Context, name: String) : DataBase {
            return Room.databaseBuilder(ctx.applicationContext, DataBase::class.java, name).build()
        }
    }
}