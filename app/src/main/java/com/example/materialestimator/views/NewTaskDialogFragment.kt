package com.example.materialestimator.views

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.materialestimator.R
import com.example.materialestimator.models.entities.Task
import com.example.materialestimator.viewModels.ProjectsViewModel
import java.util.*


/**
 * The key to to building a full screen dialog is to apply a new style which extends
 * a dialog style and includes the windowIsFloating = false attribute.
 */
class NewTaskDialogFragment: DialogFragment(), OnDateSetListener {
    private lateinit var startDateEt: EditText
    private lateinit var startDate: Date
    private val vm: ProjectsViewModel by viewModels()
    private val months =
        arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Dialog_FullScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.dialog_fragment_new_task, container, false)
        startDateEt = v.findViewById<EditText>(R.id.start_date_et)
        startDateEt.setOnClickListener{
            showDatePickerDialog()
        }
        val addBtn = v.findViewById<Button>(R.id.add_task_btn)
        addBtn.setOnClickListener {
            createNewTask(v)
            closeDialog()
        }
        val cancelBtn = v.findViewById<Button>(R.id.cancel_task_btn)
        cancelBtn.setOnClickListener {
            closeDialog()
        }
        return v
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        // Add animations and set view resize when keyboard appears to show buttons.
        val window = requireDialog().window
        window?.setWindowAnimations(R.style.DialogAnimation)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

    }

    private fun createNewTask(v: View) {
        val projectID = arguments?.getInt("Key")
        val title = v.findViewById<EditText>(R.id.task_title_et).text.toString()
        val desc = v.findViewById<EditText>(R.id.task_desc_et).text.toString()
        val task = Task(title = title, desc = desc, startDate = startDate , projectid = projectID!!)
        vm.insertTask(task)
        closeDialog()
    }

    private fun closeDialog() {
        requireDialog().dismiss()
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]
        val dialog = DatePickerDialog(
            requireContext(),
            this,
            year, month, day
        )
        dialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // create date object using date set by user
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        startDate = calendar.time
        startDateEt.setText(startDate.toString())

    }

}