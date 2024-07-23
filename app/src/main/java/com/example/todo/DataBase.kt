package com.example.todo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database (entities = [Todo::class], version = 2)
@TypeConverters(DbConverter::class)
abstract class DataBase : RoomDatabase() {
    abstract fun getDAO(): DAO

    companion object {
        private var INSTANCE: DataBase? = null

        fun getDB(context: Context, dbName: String): DataBase {
            if (INSTANCE == null) {
                synchronized(DataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java,
                        dbName
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}