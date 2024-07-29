package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.todo.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var frameLayout: FrameLayout
    private lateinit var bnv: BottomNavigationView
    private lateinit var binding: ActivityMainBinding

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