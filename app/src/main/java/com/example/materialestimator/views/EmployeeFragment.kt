package com.example.materialestimator.views

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.materialestimator.R
import com.example.materialestimator.viewModels.EmployeesViewModel

class EmployeeFragment : Fragment(R.layout.fragment_employee) {
    private val employeeVM: EmployeesViewModel by viewModels()
    private var employeeId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If there is a saved id then restore it, else use the one that was passed in.
        employeeId = savedInstanceState?.getLong("key") ?: arguments?.getLong("key")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Toolbar
        val toolbar = view.findViewById(R.id.employee_toolbar) as Toolbar
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        toolbar.title = "Employee"
        employeeVM.get(employeeId).observe(viewLifecycleOwner) { employee ->

            // Set data to views
            val imageIv = view.findViewById(R.id.employee_image_iv) as ImageView
            val imgId = resources.getIdentifier(
                employee.image,
                "drawable",
                context?.packageName
            )
            imageIv.setImageResource(imgId)
            val nameET = view.findViewById(R.id.employee_name_et) as EditText
            nameET.setText(employee.name)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("key", employeeId)
    }

}