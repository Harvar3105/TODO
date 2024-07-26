package com.example.todo

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.todo.databinding.FragmentAddBinding
import com.example.todo.databinding.FragmentListBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddFragment : Fragment() {

    private val addViewModel: AddViewModel by viewModels { ViewModelFactory((requireActivity().application as MyApp).database) }

    private lateinit var binding: FragmentAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dateTime.inputType = InputType.TYPE_NULL
        binding.dateTime.setOnClickListener { showDateTimeDialog(binding.dateTime) }

        binding.saveButton.setOnClickListener { saveItem() }
    }

    private fun saveItem() {
        if (binding.name.text.toString() == "" || binding.description.text.toString() == "" ||
            binding.dateTime.text.toString() == "") return

        val name = binding.name.text.toString()
        val description = binding.description.text.toString()
        val date = LocalDateTime.parse(binding.dateTime.text.toString(), DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"))

        if (name.isNotEmpty() && description.isNotEmpty() && binding.dateTime.text.isNotEmpty()) {
            val item = TodoDTO(
                name = name,
                description = description,
                creationDate = LocalDateTime.now(),
                date = date,
                isCompleted = false
            )
            addViewModel.saveToDB(item)
        }
        activity?.supportFragmentManager?.popBackStack()
        activity?.supportFragmentManager?.clearBackStack("Clear after adding new")
    }

    private fun showDateTimeDialog(date_time_in: EditText) {
        val calendar: Calendar = Calendar.getInstance()
        val dateSetListener =
            OnDateSetListener { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val timeSetListener =
                    OnTimeSetListener { view, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)
                        val simpleDateFormat = SimpleDateFormat("yy-MM-dd HH:mm", Locale.ENGLISH)
                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()))
                    }
                TimePickerDialog(
                    this.requireContext(),
                    timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false
                ).show()
            }
        DatePickerDialog(
            this.requireContext(),
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    companion object {
        fun newInstance(args: Bundle?) : ListFragment {
            val listFragment = ListFragment()
            listFragment.arguments = args
            return listFragment
        }
    }
}