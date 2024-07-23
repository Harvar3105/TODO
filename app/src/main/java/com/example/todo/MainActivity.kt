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
    private lateinit var db: DataBase
    private lateinit var bnv: BottomNavigationView
    private lateinit var binding: ActivityMainBinding
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DataBase.getDB(this, "mainDb")
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
                    refreshData()
                    replaceFragment(ListFragment.newInstance(bundle))
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

        refreshData()
    }

    private fun refreshData(){
        bundle = Bundle()
        db.getDAO().getAllItems().asLiveData().observe(this, Observer {
            val dtos = it.map { elem -> TodoDTO.toDTO(elem) }
            Log.i("list", "Data from db: $dtos")
            bundle.putParcelableArrayList("items", ArrayList(dtos))
        })
    }

    private fun saveToDB(item: TodoDTO){
        CoroutineScope(Dispatchers.IO).launch {
            if (!::db.isInitialized) {
                db = DataBase.getDB(this@MainActivity, "mainDb")
            }
            var item = TodoDTO.fromDTO(item)
            if (item.id == 0L) item.id = null
            db.getDAO().insertItem(item)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayoutMain, fragment)
        fragmentTransaction.commit()
    }
}