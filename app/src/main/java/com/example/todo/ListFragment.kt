package com.example.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView

class ListFragment : Fragment() {

    private lateinit var todosContainer: ListView
    private val todos: MutableList<Todo> = mutableListOf();
    private lateinit var adapter: ArrayAdapter<Todo>
    private lateinit var db: DAO

    companion object {
        fun newInstance(args: Bundle?) : ListFragment {
            val listFragment = ListFragment()
            listFragment.arguments = args
            return listFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todosContainer = view.findViewById(R.id.container)

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, todos)
        todosContainer.adapter = adapter

    }

}