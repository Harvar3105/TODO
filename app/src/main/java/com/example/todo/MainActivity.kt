package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {

//    private val menuButton: Button = findViewById(R.id.menuButton);
//    private val navigationLayout: DrawerLayout = findViewById(R.id.navigationMenu);
//    private val todosContainer: ListView = findViewById(R.id.container);
    private lateinit var menuButton: Button
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var todosContainer: ListView

    private val todos: MutableList<Todo> = mutableListOf();
    private lateinit var adapter: ArrayAdapter<Todo>
    private lateinit var db: DAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonLayout = layoutInflater.inflate(R.layout.header_layout, null)
        menuButton = buttonLayout.findViewById(R.id.menuButton)
        val testButton: Button = findViewById(R.id.testButton)

        drawerLayout = findViewById(R.id.navigationMenu)
        todosContainer = findViewById(R.id.container)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, todos)
        todosContainer.adapter = adapter

        testButton.setOnClickListener { adapter.insert(Todo(name = "ABC"), 0) }
        menuButton.setOnClickListener {
            toggleDrawer()
        }

        db = DAO(this, null)
    }

    private fun toggleDrawer() {
        adapter.insert(Todo(name = "ABC"), 0)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}