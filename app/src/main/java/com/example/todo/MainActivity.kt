package com.example.todo

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ListView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    private lateinit var frameLayout: FrameLayout
    private lateinit var db: DAO
    private lateinit var BNV: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frameLayout = findViewById(R.id.frameLayoutMain)
        BNV = findViewById(R.id.bottomNavigationView)
        BNV.setOnItemSelectedListener { item ->
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

        db = DAO(this, null)
    }

    private fun createListFragment(): ListFragment {
        val bundle = Bundle()
        bundle.putParcelable(db)
        return ListFragment.newInstance(args = bundle)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayoutMain, fragment)
        fragmentTransaction.commit()
    }
}