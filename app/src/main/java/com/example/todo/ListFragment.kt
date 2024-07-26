package com.example.todo

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.todo.databinding.FragmentListBinding
import java.time.LocalDateTime

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val listViewModel: ListViewModel by viewModels { ViewModelFactory((requireActivity().application as MyApp).database) }

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
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ListAdapter(
            requireContext(),
            mutableListOf(),
            { todo -> update(todo) },
            { todo -> delete(todo) },
            LocalDateTime.now()
        )
        binding.container.adapter = adapter

        listViewModel.loadTodos()

        listViewModel.todos.observe(viewLifecycleOwner, Observer { todos ->
            adapter.clear()
            adapter.addAll(todos)
            adapter.notifyDataSetChanged()
        })
    }

    private fun update(todo: TodoDTO){
        listViewModel.updateTodo(todo)
    }

    private fun delete(todo: TodoDTO){
        listViewModel.deleteTodo(todo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}