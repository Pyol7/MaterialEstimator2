package com.example.materialestimator.views.task

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.materialestimator.R
import com.example.materialestimator.databinding.DialogFragmentNewTaskBinding
import com.example.materialestimator.models.entities.Task
import com.example.materialestimator.utilities.Converters
import com.example.materialestimator.viewModels.ProjectsViewModel
import com.google.android.material.textfield.TextInputEditText
import java.util.*

/**
 * The key to building a full screen dialog is to apply a new style which extends
 * a dialog style and includes the windowIsFloating = false attribute.
 */
class NewTaskDialogFragment : DialogFragment(R.layout.dialog_fragment_new_task) {
    private val vm: ProjectsViewModel by viewModels()

    // The backing field is needed for:
    // To avoid non null assertions everywhere the binding instance is used.
    // To avoid a memory leak by setting the binding instance to null in onDestroyView().
    private var _binding: DialogFragmentNewTaskBinding? = null
    private val binding get() = _binding!!
    private var startDateEt: TextInputEditText? = null
    private var completionDateEt: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set style required for full screen dialog
        setStyle(STYLE_NORMAL, R.style.Theme_Dialog_FullScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentNewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        // Set up toolbar
        val toolbar = binding.addTaskToolbar
        toolbar.setNavigationOnClickListener { dismiss() }
        toolbar.title = "New Task"
        toolbar.inflateMenu(R.menu.add_task_full_screen_menu)
        toolbar.setOnMenuItemClickListener() {
            createAndInsetNewTask(view)
            true
        }

        // Set dialog animation and allow scrolling of view when softKeyboard is visible
        requireDialog().window?.setWindowAnimations(R.style.DialogAnimation)
        requireDialog().window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        val seekBar = binding.seekBar
        val percentCompletion = binding.taskPercentCompletedEt
        percentCompletion.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isEmpty() == true) {
                    percentCompletion.setText("0")
                    percentCompletion.selectAll()
                }
                if (s?.isNotEmpty() == true) {
                    seekBar.progress = Integer.parseInt(s.toString())
                    if (Integer.parseInt(s.toString()) > 100) {
                        percentCompletion.setText("100")
                    }
                    percentCompletion.setSelection(percentCompletion.text.toString().length)
                } else {
                    seekBar.progress = 0
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    percentCompletion.setText(progress.toString())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Set clickListeners that shows the appropriate date picker
        startDateEt = binding.taskStartDateEt
        startDateEt!!.setOnClickListener {
            showDatePickerDialog(startDateDatePickerListener)
        }
        completionDateEt = binding.taskCompletionDateEt
        completionDateEt!!.setOnClickListener {
            showDatePickerDialog(completionDateDatePickerListener)
        }

    }

    // Assigns the DatePickerDialog.onDateSetListener as an anonymous object to a variable.
    private val startDateDatePickerListener =
        OnDateSetListener { _, year, month, dayOfMonth ->
            startDateEt?.setText(Converters.yearMonthDayToString(year, month, dayOfMonth))
        }

    private val completionDateDatePickerListener =
        OnDateSetListener { _, year, month, dayOfMonth ->
            completionDateEt?.setText(Converters.yearMonthDayToString(year, month, dayOfMonth))
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createAndInsetNewTask(v: View) {
        val title = binding.taskTitleEt.text.toString()
        val description = binding.taskDescEt.text.toString()
        val estimatedDays = Converters.stringToInteger(binding.taskEstimatedDaysEt.text.toString())
        val estimatedHours =
            Converters.stringToInteger(binding.taskEstimatedHoursEt.text.toString())
        val skilledLabour = Converters.stringToInteger(binding.taskSkilledLabourEt.text.toString())
        val unSkilledLabour =
            Converters.stringToInteger(binding.taskUnskilledLabourEt.text.toString())
        val percentCompleted =
            Converters.stringToInteger(binding.taskPercentCompletedEt.text.toString())
        val completionDate = Converters.stringToDate(binding.taskCompletionDateEt.text.toString())
        val startDate = Converters.stringToDate(binding.taskStartDateEt.text.toString())
        val projectId = arguments?.getInt("Key")

        val task = Task(
            title = title,
            description = description,
            estimatedDays = estimatedDays,
            estimatedHours = estimatedHours,
            skilledLabour = skilledLabour,
            unSkilledLabour = unSkilledLabour,
            percentCompleted = percentCompleted,
            startDate = startDate,
            projectID = projectId!!
        )
        vm.insertTask(task)
        dismiss()
    }

    /**
     * Displays the date picker dialog with the current date and passes
     * the selected date to the listener.
     */
    private fun showDatePickerDialog(listener: OnDateSetListener) {
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