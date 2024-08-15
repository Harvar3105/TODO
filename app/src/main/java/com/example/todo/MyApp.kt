package com.example.todo

import android.app.Application
import androidx.datastore.preferences.preferencesDataStore

class MyApp : Application() {
    val database by lazy { DataBase.getDB(this, "mainDb") }
    val dataStore by preferencesDataStore(name = "settings")
}