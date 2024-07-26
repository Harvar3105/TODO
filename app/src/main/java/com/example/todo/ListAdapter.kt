package com.example.todo

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ListAdapter(
    context: Context,
    private val items: List<TodoDTO>,
    private val updateListener: (TodoDTO) -> Unit,
    private val deleteListener: (TodoDTO) -> Unit,
    private val time: LocalDateTime
) :
    ArrayAdapter<TodoDTO>(context, R.layout.list_item, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        val todo = items[position]

        val name: TextView = view.findViewById(R.id.item_name)
        name.text = "\t${todo.name}" ?: ""
        if (todo.isCompleted) name.setTextColor(Color.GREEN) else name.setTextColor(Color.RED)

        val description: TextView = view.findViewById(R.id.item_description)
        description.text = "\t${todo.description}" ?: ""

        val date: TextView = view.findViewById(R.id.set_date)
        date.text = todo.creationDate?.format(
            DateTimeFormatter.ofPattern("yyyy.MM.dd - HH:mm"))

        val estimatedDate: TextView = view.findViewById(R.id.estimated_date)
        estimatedDate.text = todo.date?.format(
            DateTimeFormatter.ofPattern("yyyy.MM.dd - HH:mm"))
        if (time.isAfter(todo.date)) estimatedDate.setTextColor(Color.RED) else estimatedDate.setTextColor(Color.GREEN)

//        val changeState: Button = view.findViewById(R.id.item_edit)
//        changeState.setOnClickListener {
//            todo.isCompleted = true
//            updateListener(todo)
//        }

        val delete: Button = view.findViewById(R.id.delete)
        if (todo.isCompleted){
            delete.setText(R.string.delete)
            delete.setOnClickListener {
                deleteListener(todo)
            }
        } else {
            delete.setText(R.string.mark_completed)
            delete.setOnClickListener {
                todo.isCompleted = true
                updateListener(todo)
            }
        }


        return view
    }
}