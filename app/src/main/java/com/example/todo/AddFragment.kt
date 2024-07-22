package com.example.todo

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.todo.databinding.FragmentAddBinding
import com.example.todo.databinding.FragmentListBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var dateTimeText: EditText
    private lateinit var name: EditText
    private lateinit var description: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateTimeText = binding.dateTime
        dateTimeText.inputType = InputType.TYPE_NULL
        dateTimeText.setOnClickListener { showDateTimeDialog(dateTimeText) }

        name = binding.name
        description = binding.description

        saveButton = binding.saveButton
        saveButton.setOnClickListener { saveItem() }

    }

    private fun saveItem(){
        if (!name.text.any() || name.text == null) return
        if (!description.text.any() || description.text == null) return
        if (!dateTimeText.text.any() || dateTimeText.text == null) return

        var dto: TodoDTO = TodoDTO(
            name = name.text.toString(),
            description = description.text.toString(),
            creationDate = LocalDateTime.now(),
            date = LocalDateTime.parse(dateTimeText.text.toString(), DateTimeFormatter.ofPattern("yy-MM-dd HH:mm")),
            isCompleted = false
        )

        val intent = Intent(this.context, MainActivity::class.java).apply { putExtra("ItemToAdd", dto) }
        startActivity(intent)
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