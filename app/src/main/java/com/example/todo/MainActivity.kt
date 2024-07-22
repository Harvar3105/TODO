package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.todo.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var frameLayout: FrameLayout
    private lateinit var db: DataBase
    private lateinit var bnv: BottomNavigationView
    private lateinit var binding: ActivityMainBinding
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item: TodoDTO? = intent.getParcelableExtra("ItemToAdd")
        if (item != null) {
            saveToDB(item)
            intent.removeExtra("ItemToAdd")
        }

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

        db = DataBase.getDB(this, "mainDb")
        bundle = Bundle()
        db.getDAO().getAllItems().asLiveData().observe(this, Observer {
            val dtos = it.map { elem -> TodoDTO.toDTO(elem) }
            bundle.putParcelableArrayList("items", ArrayList(dtos))
        })
    }

    private fun saveToDB(item: TodoDTO){
        db.getDAO().insertItem(TodoDTO.fromDTO(item))
        db.close()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayoutMain, fragment)
        fragmentTransaction.commit()
    }
}