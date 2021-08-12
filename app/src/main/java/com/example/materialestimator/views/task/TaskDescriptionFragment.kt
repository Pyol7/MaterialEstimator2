package com.example.materialestimator.views.task

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.materialestimator.R
import com.example.materialestimator.storage.local.entities.Task
import com.example.materialestimator.utilities.Converters
import com.example.materialestimator.viewModels.TasksViewModel
import java.util.*

/**
 * Responsibilities:
 * Displays data that describes the Task.
 * Updates the task when onPaused() is called.
 */

class TaskDescriptionFragment : Fragment(R.layout.fragment_task_description) {
    private val tasksVm: TasksViewModel by viewModels()
    private lateinit var titleEt: EditText
    private lateinit var descriptionEt: EditText
    private lateinit var startDateEt: EditText
    private lateinit var estimatedDaysEt: EditText
    private lateinit var estimatedHoursEt: EditText
    private lateinit var completionDateEt: EditText
    private lateinit var task: Task
    private var taskId = 0L

    companion object {
        fun newInstance(taskId: Long?) = TaskDescriptionFragment().apply {
            arguments = bundleOf("key" to taskId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the selected task id passed in via construction injection
        taskId = arguments?.getLong("key")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields(view)
        /**
         * Passing the OnDateSetListener as an anonymous object into showDatePickerDialog() allows
         * for multiple views to use the same date picker dialog.
         * Alternatively, the listener can also be assigned to a variable as an anonymous object
         * and passed into showDatePickerDialog(). The later is more concise.
         */
        startDateEt.setOnClickListener {
            showDatePickerDialog { _, year, month, day ->
                startDateEt.setText(Converters.yearMonthDayToString(year, month, day))
            }
        }
        completionDateEt.setOnClickListener {
            showDatePickerDialog { _, year, month, day ->
                completionDateEt.setText(Converters.yearMonthDayToString(year, month, day))
            }
        }
        tasksVm.get(taskId).observe(viewLifecycleOwner) {
            task = it
            titleEt.setText(task.title)
            descriptionEt.setText(task.description)
            startDateEt.setText(task.startDate?.let { Converters.dateToString(it) })
            estimatedDaysEt.setText(task.estimatedDays.toString())
            estimatedHoursEt.setText(task.estimatedHours.toString())
            completionDateEt.setText(task.completionDate?.let { Converters.dateToString(it) })
        }
    }

    override fun onPause() {
        super.onPause()
        saveAllFields()
    }

    private fun initFields(view: View) {
        titleEt = view.findViewById(R.id.task_title_et)
        descriptionEt = view.findViewById(R.id.task_desc_et)
        startDateEt = view.findViewById(R.id.task_start_date_et)
        estimatedDaysEt = view.findViewById(R.id.task_estimated_days_et)
        estimatedHoursEt = view.findViewById(R.id.task_estimated_hours_et)
        completionDateEt = view.findViewById(R.id.task_completion_date_et)
    }

    private fun saveAllFields() {
        task.title = titleEt.text.toString()
        task.description = descriptionEt.text.toString()
        task.startDate = Converters.stringToDate(startDateEt.text.toString())
        task.estimatedDays = Converters.stringToInteger(estimatedDaysEt.text.toString())
        task.estimatedHours = Converters.stringToInteger(estimatedHoursEt.text.toString())
        task.completionDate = Converters.stringToDate(completionDateEt.text.toString())
        tasksVm.update(task)
    }

    /**
     * Displays the date picker dialog with the current date and passes
     * the selected date to the listener.
     */
    private fun showDatePickerDialog(listener: DatePickerDialog.OnDateSetListener) {
        // Get the current date
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]
        // Start the date picker with the current date
        DatePickerDialog(
            requireContext(),
            listener,
            year, month, day
        ).show()
    }
}
