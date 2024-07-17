package com.example.todo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database (entities = [Todo::class], version = 1)
@TypeConverters(DbConverter::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun getDAO(): DAO

    companion object {
        fun getDB(ctx: Context, name: String) : RoomDB {
            return Room.databaseBuilder(ctx.applicationContext, RoomDB::class.java, name).build()
        }
    }
}