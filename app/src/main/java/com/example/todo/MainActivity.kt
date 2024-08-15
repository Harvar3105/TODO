package com.example.todo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.todo.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var frameLayout: FrameLayout
    private lateinit var bnv: BottomNavigationView
    private lateinit var binding: ActivityMainBinding

    private val dataStore by lazy {
        (application as MyApp).dataStore
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        frameLayout = binding.frameLayoutMain
        bnv = binding.bottomNavigationView
        bnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeButton -> {
                    replaceFragment(ListFragment())
                    true
                }
                R.id.addButton -> {
                    replaceFragment(AddFragment())
                    true
                }
                R.id.settingsButton -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
        replaceFragment(ListFragment())
        checkMode()
    }

    private fun checkMode(){
        lifecycleScope.launch {
            try {
                val isDarkTheme = readThemePreference().first()
                if (isDarkTheme) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            } catch (e: IOException) {
                Log.e("MainActivity", "Failed to read theme preference", e)
            }
        }
    }

    private fun readThemePreference(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[booleanPreferencesKey("isDarkTheme")] ?: false
            }
    }

    public fun switchToEdit(item: TodoDTO){
        val fragment = AddFragment()
        replaceFragment(fragment)
        fragment.fillData(item)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayoutMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        fragmentManager.executePendingTransactions()
    }
}