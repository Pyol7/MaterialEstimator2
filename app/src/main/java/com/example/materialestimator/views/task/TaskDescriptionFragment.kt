package com.example.materialestimator.views.task

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.materialestimator.R
import com.example.materialestimator.TAG
import com.example.materialestimator.utilities.Converters
import com.example.materialestimator.viewModels.TaskViewModel
import java.util.*

class TaskDescriptionFragment : Fragment(R.layout.fragment_task_description) {
    // Initialize the vm using the parent fragment's scope (ie TaskFragment())
    private val taskVm: TaskViewModel by activityViewModels()
    private lateinit var titleEt: EditText
    private lateinit var descriptionEt: EditText
    private lateinit var startDateEt: EditText
    private lateinit var estimatedDaysEt: EditText
    private lateinit var estimatedHoursEt: EditText
    private lateinit var completionDateEt: EditText

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

        val t = taskVm.selectedTask
            titleEt.setText(t.title)
            descriptionEt.setText(t.description)
            startDateEt.setText(t.startDate?.let { Converters.dateToString(it) })
            estimatedDaysEt.setText(t.estimatedDays.toString())
            estimatedHoursEt.setText(t.estimatedHours.toString())
            completionDateEt.setText(t.completionDate?.let { Converters.dateToString(it) })
        }


    override fun onPause() {
        super.onPause()
        saveAllFields()
    }

    private fun initFields(view: View){
        titleEt = view.findViewById(R.id.task_title_et)
        descriptionEt = view.findViewById(R.id.task_desc_et)
        startDateEt = view.findViewById(R.id.task_start_date_et)
        estimatedDaysEt = view.findViewById(R.id.task_estimated_days_et)
        estimatedHoursEt = view.findViewById(R.id.task_estimated_hours_et)
        completionDateEt = view.findViewById(R.id.task_completion_date_et)
    }

    private fun saveAllFields(){
        val t = taskVm.selectedTask
        t.title = titleEt.text.toString()
        t.description = descriptionEt.text.toString()
        t.startDate = Converters.stringToDate(startDateEt.text.toString())
        t.estimatedDays = Converters.stringToInteger(estimatedDaysEt.text.toString())
        t.estimatedHours = Converters.stringToInteger(estimatedHoursEt.text.toString())
        t.completionDate = Converters.stringToDate(completionDateEt.text.toString())
        taskVm.update(t)
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
