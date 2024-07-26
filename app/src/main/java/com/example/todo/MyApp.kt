package com.example.todo

import android.app.Application

class MyApp : Application() {
    val database by lazy { DataBase.getDB(this, "mainDb") }
}