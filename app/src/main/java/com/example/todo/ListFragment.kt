package com.example.todo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.todo.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val todos: MutableList<TodoDTO> = mutableListOf();
    private lateinit var adapter: ArrayAdapter<TodoDTO>

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
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = arguments?.getParcelableArrayList<TodoDTO>("items") ?: ArrayList()
        items.let { todos.addAll(items) }

        Log.i("list", "argumants contain: $items")
        Log.i("list", "todos contain: $todos")

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, todos)
        binding.container.adapter = adapter

        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}