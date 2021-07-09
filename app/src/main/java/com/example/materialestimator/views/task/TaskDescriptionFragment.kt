package com.example.materialestimator.views.task

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.utilities.Converters
import com.example.materialestimator.viewModels.TaskViewModel
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class TaskDescriptionFragment : Fragment(R.layout.fragment_task_description) {
    // Initialize the vm using the parent fragment's scope (ie TaskFragment())
    private val vm: TaskViewModel by viewModels( { requireParentFragment()} )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleEt = view.findViewById<EditText>(R.id.task_title_et)
        val descriptionEt = view.findViewById<EditText>(R.id.task_desc_et)
        val startDateEt = view.findViewById<EditText>(R.id.task_start_date_et)
        val estimatedDaysEt = view.findViewById<EditText>(R.id.task_estimated_days_et)
        val estimatedHoursEt = view.findViewById<EditText>(R.id.task_estimated_hours_et)
        val completionDateEt = view.findViewById<EditText>(R.id.task_completion_date_et)

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

        vm.selectedTask.observe(viewLifecycleOwner) { selectedTask ->
            Log.i(TAG, "TaskDescriptionFragment: selectedTask = $selectedTask")
            titleEt.setText(selectedTask.title)
            descriptionEt.setText(selectedTask.description)
            startDateEt.setText(selectedTask.startDate?.let { Converters.dateToString(it) })
            estimatedDaysEt.setText(selectedTask.estimatedDays.toString())
            estimatedHoursEt.setText(selectedTask.estimatedHours.toString())
        }
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
