package com.example.materialestimator.temp

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
import com.example.materialestimator.viewModels.ProjectsViewModel

/**
 * The key to building a full screen dialog is to apply a new style which extends
 * a dialog style and includes the windowIsFloating = false attribute.
 */
class _NewTaskDialogFragment : DialogFragment(R.layout.dialog_fragment_new_task) {
    private val vm: ProjectsViewModel by viewModels()

    // The backing field is needed for:
    // To avoid non null assertions everywhere the binding instance is used.
    // To avoid a memory leak by setting the binding instance to null in onDestroyView().
    private var _binding: DialogFragmentNewTaskBinding? = null
    private val binding get() = _binding!!

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
    }
}